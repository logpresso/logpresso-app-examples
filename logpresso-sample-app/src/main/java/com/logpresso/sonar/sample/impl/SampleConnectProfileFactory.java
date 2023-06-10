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
			return "����";
		return "Sample";
	}

	@Override
	public String getDescription(Locale locale) {
		if (locale != null && locale.equals(Locale.KOREAN))
			return "���� ���� ������ �����մϴ�.";
		return "Manage connection properties of sample app.";
	}

	@Override
	public Set<String> getProtectedConfigKeys() {
		return Set.of("api_key");
	}

	@Override
	public List<LoggerConfigOption> getConfigOptions() {
		LoggerConfigOption apiKey = new MutableStringConfigType("api_key", t("API key", "API Ű"),
				t("GUID format", "GUID ����"), true);
		LoggerConfigOption endpoint = new MutableStringConfigType("endpoint", t("Endpoint", "��������Ʈ"),
				t("REST API URL", "REST API �ּ�"), true);

		return Arrays.asList(apiKey, endpoint);
	}

	@Override
	public String describeConfigs(ConnectProfile profile, Locale locale) {
		String endpoint = profile.getString("endpoint");
		if (locale != null && locale.equals(Locale.KOREAN))
			return "��������Ʈ: " + endpoint;
		return "Endpoint: " + endpoint;
	}

	private Map<Locale, String> t(String en, String ko) {
		Map<Locale, String> m = new HashMap<Locale, String>();
		m.put(Locale.ENGLISH, en);
		m.put(Locale.KOREAN, ko);
		return m;
	}
}
