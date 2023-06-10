package com.logpresso.sonar.sample.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.araqne.logdb.QueryParserService;
import org.logpresso.api.profile.ConnectProfileService;

import com.logpresso.sonar.sample.query.SampleCreateSubnetGroupCommandParser;
import com.logpresso.sonar.sample.query.SampleQueryCommandParser;
import com.logpresso.sonar.sample.query.SampleSubnetGroupsCommandParser;

@Component(name = "sample-service")
@Instantiate
public class SampleServiceImpl {

	@Requires
	private QueryParserService queryParserService;

	@Requires
	private ConnectProfileService connectProfileService;

	private SampleQueryCommandParser subnetGroupsParser = new SampleSubnetGroupsCommandParser();
	private SampleQueryCommandParser createSubnetGroupParser = new SampleCreateSubnetGroupCommandParser();

	@Validate
	public void start() {
		for (SampleQueryCommandParser parser : getParsers()) {
			parser.setConnectProfileService(connectProfileService);
			queryParserService.addCommandParser(parser);
		}
	}

	@Invalidate
	public void stop() {
		if (queryParserService == null)
			return;

		for (SampleQueryCommandParser parser : getParsers()) {
			parser.setConnectProfileService(null);
			queryParserService.removeCommandParser(parser);
		}
	}

	private List<SampleQueryCommandParser> getParsers() {
		return Arrays.asList(subnetGroupsParser, createSubnetGroupParser);
	}
}
