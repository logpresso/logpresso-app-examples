package com.logpresso.sonar.sample.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.araqne.log.api.AbstractLoggerFactory;
import org.araqne.log.api.Logger;
import org.araqne.log.api.LoggerConfigOption;
import org.araqne.log.api.LoggerSpecification;
import org.araqne.log.api.MutableStringConfigType;
import org.logpresso.api.profile.ConnectProfileService;

@Component(name = "sample-logger-factory")
@Provides
@Instantiate
public class SampleLoggerFactory extends AbstractLoggerFactory {

	@Requires
	private ConnectProfileService connectProfileService;

	@Override
	public String getName() {
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
			return "로그프레소에서 시스템 로그를 수집합니다.";
		return "Collect system logs from Logpresso";
	}

	@Override
	public Collection<LoggerConfigOption> getConfigOptions() {
		LoggerConfigOption profile = new MutableStringConfigType("profile", t("Profile", "프로파일"),
				t("Sample connect profile code", "샘플 접속 프로파일 식별자"), true);
		return Arrays.asList(profile);
	}

	@Override
	protected Logger createLogger(LoggerSpecification spec) {
		return new SampleLogger(spec, this, connectProfileService);
	}

	private Map<Locale, String> t(String en, String ko) {
		Map<Locale, String> m = new HashMap<Locale, String>();
		m.put(Locale.ENGLISH, en);
		m.put(Locale.KOREAN, ko);
		return m;
	}
}
