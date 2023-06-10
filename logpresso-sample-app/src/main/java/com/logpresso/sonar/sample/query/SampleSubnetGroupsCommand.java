package com.logpresso.sonar.sample.query;

import java.io.IOException;
import java.net.URL;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.araqne.log.api.RestApiClient;
import org.araqne.log.api.RestApiResponse;
import org.araqne.logdb.Row;
import org.logpresso.api.profile.ConnectProfile;

public class SampleSubnetGroupsCommand extends SampleDriverQueryCommand {

	public SampleSubnetGroupsCommand(SampleParams params) {
		super(params);
	}

	@Override
	public List<String> getFieldOrder() {
		return Arrays.asList("guid", "name", "description", "subnet_count", "user_name", "user_guid", "created",
				"updated");
	}

	@Override
	public String getName() {
		return "sample-subnet-groups";
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void run(ConnectProfile profile) throws IOException {
		RestApiClient client = newClient(profile);
		URL url = toUrl(profile, "/api/sonar/subnet-groups");
		RestApiResponse resp = client.get(url);

		List<Object> groups = (List<Object>) resp.getJson().get("subnet_groups");
		for (Object o : groups) {
			Map<String, Object> m = (Map<String, Object>) o;
			m.put("created", parseDate((String) m.get("created")));
			m.put("updated", parseDate((String) m.get("update")));

			pushPipe(new Row(m));
		}
	}

	private Date parseDate(String s) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
		return df.parse(s, new ParsePosition(0));
	}

}
