package com.logpresso.sonar.sample.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.araqne.log.api.AbstractLogger;
import org.araqne.log.api.Log;
import org.araqne.log.api.LoggerFactory;
import org.araqne.log.api.LoggerSpecification;
import org.araqne.log.api.LoggerStatus;
import org.araqne.log.api.Reconfigurable;
import org.araqne.log.api.RestApiClient;
import org.araqne.log.api.RestApiResponse;
import org.araqne.log.api.SimpleLog;
import org.json.JSONConverter;
import org.json.JSONException;
import org.json.JSONObject;
import org.logpresso.api.profile.ConnectProfile;
import org.logpresso.api.profile.ConnectProfileService;

public class SampleLogger extends AbstractLogger implements Reconfigurable {

	private static final String CONNECT_PROFILE_TYPE = "sample";
	private final org.slf4j.Logger slog = org.slf4j.LoggerFactory.getLogger(SampleLogger.class);

	private ConnectProfileService connectProfileService;

	public SampleLogger(LoggerSpecification spec, LoggerFactory factory, ConnectProfileService connectProfileService) {
		super(spec, factory);
		this.connectProfileService = connectProfileService;
	}

	@Override
	public void onConfigChange(Map<String, String> oldConfigs, Map<String, String> newConfigs) {
		// ignored. there are no instance variables for state management.
	}

	@Override
	protected void runOnce() {
		Map<String, Object> states = getStates();
		try {
			while (getStatus() == LoggerStatus.Running) {
				int count = loadSystemLogs(states);
				if (count < 0)
					break;
			}
		} catch (Throwable t) {
			if (slog.isDebugEnabled())
				slog.debug("sample: cannot load logpresso sytem logs", t);

			setTemporaryFailure(t);
		}
	}

	private int loadSystemLogs(Map<String, Object> states) throws IOException, JSONException, ParseException {
		int count = 0;

		// load last record marker
		String lastTimestamp = (String) states.get("last_timestamp");
		RestApiResponse resp = invokeRestApi(lastTimestamp);

		String response = resp.getData();
		if (response.trim().isEmpty())
			return 0;

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
		String fullName = getFullName();
		List<Log> logs = new LinkedList<Log>();

		for (String line : response.split("\n")) {
			Map<String, Object> entry = JSONConverter.parse(new JSONObject(line));
			lastTimestamp = (String) entry.remove("timestamp");
			entry.remove("_time");

			Date time = df.parse(lastTimestamp);
			logs.add(new SimpleLog(time, fullName, entry));
		}

		// transfer collected logs
		writeBatch(logs.toArray(new Log[0]));

		// save last record marker
		states.put("last_timestamp", lastTimestamp);
		setStates(states);

		setTemporaryFailure(null);

		return count;
	}

	private RestApiResponse invokeRestApi(String lastTimestamp) throws IOException {
		String profileCode = getConfigs().get("profile");
		ConnectProfile profile = connectProfileService.getConnectProfile(null, CONNECT_PROFILE_TYPE, profileCode);

		String endpoint = profile.getString("endpoint");
		String apiKey = profile.getString("api_key");

		RestApiClient client = new RestApiClient();
		client.setHeader("Authorization", "Bearer " + apiKey);

		URL url = getQueryUrl(endpoint, lastTimestamp);

		// if logger is running on the target logpresso and debug is enabled,
		// new debug logs will be collected indefinitely.
		if (slog.isDebugEnabled())
			slog.debug("sample: logger [{}] requests url [{}]", getFullName(), url);

		RestApiResponse resp = client.get(url);
		return resp;
	}

	private URL getQueryUrl(String endpoint, String lastTimestamp) {
		try {
			String queryString = buildQueryString(lastTimestamp);
			return new URL(endpoint + "/api/sonar/query.json?q=" + URLEncoder.encode(queryString, "utf-8"));
		} catch (MalformedURLException e) {
			throw new IllegalStateException("cannot build query url: " + e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("utf-8 is not supported", e);
		}
	}

	private String buildQueryString(String lastTimestamp) {
		String s = "system logs | eval timestamp = string(_time, \"yyyy-MM-dd HH:mm:ss.SSSZ\")";
		if (lastTimestamp != null)
			s += " | search _time > date(\"" + lastTimestamp + "\", \"yyyy-MM-dd HH:mm:ss.SSSZ\")";
		s += " | limit 1000";
		return s;
	}
}
