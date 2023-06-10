package com.logpresso.sonar.sample.query;

import org.logpresso.api.profile.query.ConnectProfileParams;

public class SampleParams extends ConnectProfileParams {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
