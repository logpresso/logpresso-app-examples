package com.logpresso.sonar.sample.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.araqne.log.api.LoggerConfigOption;
import org.araqne.log.api.MutableStringConfigType;
import org.logpresso.api.profile.AbstractConnectProfileFactory;
import org.logpresso.api.profile.ConnectProfile;
import org.logpresso.api.profile.ConnectProfileFactoryRegistry;

@Component(name = "sample-connect-profile-factory")
@Instantiate
public class SampleConnectProfileFactory extends AbstractConnectProfileFactory {

	@Requires
	private ConnectProfileFactoryRegistry connectProfileFactoryRegistry;

	@Validate
	public void start() {
		connectProfileFactoryRegistry.addFactory(this);
	}

	@Invalidate
	public void stop() {
		if (connectProfileFactoryRegistry != null)
			connectProfileFactoryRegistry.removeFactory(this);
	}

	@Override
	public String getType() {
		return "sample";
	}

	@Override
	public String getDisplayName(Locale locale) {
		if (locale != null && locale.equals(Locale.KOREAN))
			return "샘플";
		return "Sample";
	}

	@Override
	public String getDescription(Locale locale) {
		if (locale != null && locale.equals(Locale.KOREAN))
			return "샘플 접속 설정을 관리합니다.";
		return "Manage connection properties of sample app.";
	}

	@Override
	public Set<String> getProtectedConfigKeys() {
		return Set.of("api_key");
	}

	@Override
	public List<LoggerConfigOption> getConfigOptions() {
		LoggerConfigOption endpoint = new MutableStringConfigType("endpoint", t("Endpoint", "엔드포인트"),
				t("REST API URL", "REST API 주소"), true);
		LoggerConfigOption apiKey = new MutableStringConfigType("api_key", t("API key", "API 키"),
				t("GUID format", "GUID 형식"), true);

		return Arrays.asList(endpoint, apiKey);
	}

	@Override
	public String describeConfigs(ConnectProfile profile, Locale locale) {
		String endpoint = profile.getString("endpoint");
		if (locale != null && locale.equals(Locale.KOREAN))
			return "엔드포인트: " + endpoint;
		return "Endpoint: " + endpoint;
	}

	private Map<Locale, String> t(String en, String ko) {
		Map<Locale, String> m = new HashMap<Locale, String>();
		m.put(Locale.ENGLISH, en);
		m.put(Locale.KOREAN, ko);
		return m;
	}
}
