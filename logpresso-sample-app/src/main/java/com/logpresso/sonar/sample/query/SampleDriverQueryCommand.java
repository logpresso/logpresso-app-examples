package com.logpresso.sonar.sample.query;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.araqne.log.api.RestApiClient;
import org.araqne.logdb.DriverQueryCommand;
import org.araqne.logdb.FieldOrdering;
import org.araqne.logdb.Strings;
import org.logpresso.api.profile.ConnectProfile;

public abstract class SampleDriverQueryCommand extends DriverQueryCommand implements FieldOrdering {

	protected SampleParams params;

	public SampleDriverQueryCommand(SampleParams params) {
		this.params = params;
	}

	protected abstract void run(ConnectProfile profile) throws IOException;

	@Override
	public void run() {
		for (ConnectProfile profile : params.getProfiles()) {
			if (isCancelRequested())
				return;

			try {
				run(profile);
			} catch (Throwable t) {
				String msg = String.format("%s (profile %s) error - %s", getName(), profile.getName(), t.getMessage());
				throw new IllegalStateException(msg, t);
			}
		}
	}

	protected RestApiClient newClient(ConnectProfile profile) {
		String apiKey = profile.getString("api_key");

		RestApiClient client = new RestApiClient();
		client.setHeader("Authorization", "Bearer " + apiKey);
		return client;
	}

	protected URL toUrl(ConnectProfile profile, String path) {
		String endpoint = profile.getString("endpoint");
		String url = endpoint + path;
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new IllegalStateException("malformed sample endpoint: " + url);
		}
	}

	@Override
	public String toString() {
		String s = getName();

		if (params.getName() != null)
			s += Strings.doubleQuote(params.getName());

		return s;
	}

}
