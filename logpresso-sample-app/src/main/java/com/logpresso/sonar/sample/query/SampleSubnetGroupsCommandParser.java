package com.logpresso.sonar.sample.query;

import java.util.Locale;

import org.araqne.log.api.ValueType;
import org.araqne.logdb.QueryCommand;
import org.araqne.logdb.QueryContext;

public class SampleSubnetGroupsCommandParser extends SampleQueryCommandParser {

	public SampleSubnetGroupsCommandParser() {
		setDescription(Locale.ENGLISH, "Get subnet groups from the Logpresso server.");
		setDescription(Locale.KOREAN, "로그프레소 서버의 네트워크 대역 그룹 목록을 조회합니다.");

		setOutput("guid", ValueType.STRING, Locale.ENGLISH, "GUID", "");
		setOutput("name", ValueType.STRING, Locale.ENGLISH, "Name", "");
		setOutput("description", ValueType.STRING, Locale.ENGLISH, "Description", "");
		setOutput("subnet_count", ValueType.INT, Locale.ENGLISH, "Subnet count",
				"Number of subnet items in the group.");
		setOutput("user_name", ValueType.STRING, Locale.ENGLISH, "User name", "");
		setOutput("user_guid", ValueType.STRING, Locale.ENGLISH, "User GUID", "");
		setOutput("created", ValueType.DATE, Locale.ENGLISH, "Created", "");
		setOutput("updated", ValueType.DATE, Locale.ENGLISH, "Updated", "");

		setOutput("guid", ValueType.STRING, Locale.KOREAN, "GUID", "");
		setOutput("name", ValueType.STRING, Locale.KOREAN, "이름", "");
		setOutput("description", ValueType.STRING, Locale.KOREAN, "설명", "");
		setOutput("subnet_count", ValueType.INT, Locale.KOREAN, "항목 수", "네트워크 대역 그룹에 속한 항목 수");
		setOutput("user_name", ValueType.STRING, Locale.KOREAN, "계정 이름", "");
		setOutput("user_guid", ValueType.STRING, Locale.KOREAN, "계정 GUID", "");
		setOutput("created", ValueType.DATE, Locale.KOREAN, "생성일시", "");
		setOutput("updated", ValueType.DATE, Locale.KOREAN, "수정일시", "");
	}

	@Override
	public String getCommandName() {
		return "sample-subnet-groups";
	}

	@Override
	protected QueryCommand parse(QueryContext context, SampleParams params) {
		return new SampleSubnetGroupsCommand(params);
	}
}
