package com.logpresso.sonar.sample.query;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.araqne.log.api.RestApiClient;
import org.araqne.logdb.Row;
import org.logpresso.api.profile.ConnectProfile;

public class SampleCreateSubnetGroupCommand extends SampleDriverQueryCommand {

	public SampleCreateSubnetGroupCommand(SampleParams params) {
		super(params);
	}

	@Override
	public String getName() {
		return "sample-create-subnet-group";
	}

	@Override
	public List<String> getFieldOrder() {
		return null;
	}

	@Override
	protected void run(ConnectProfile profile) throws IOException {
		RestApiClient client = newClient(profile);
		URL url = toUrl(profile, "/api/sonar/subnet-groups");

		Map<String, Object> postParams = new HashMap<String, Object>();
		postParams.put("name", params.getName());

		client.postForm(url, postParams);
		Row row = new Row();
		row.put("name", params.getName());

		pushPipe(row);
	}
}
