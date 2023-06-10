package com.logpresso.sonar.sample.query;

import java.util.Locale;

import org.araqne.logdb.QueryCommand;
import org.araqne.logdb.QueryContext;
import org.araqne.logdb.QueryParseException;

public class SampleCreateSubnetGroupCommandParser extends SampleQueryCommandParser {

	public SampleCreateSubnetGroupCommandParser() {
		setDisplayGroup(Locale.ENGLISH, "Sample");
		setDisplayName(Locale.ENGLISH, "Create subnet group");
		setDescription(Locale.ENGLISH, "Create new subnet group in the Logpresso server.");
		setOption("name", REQUIRED, Locale.ENGLISH, "Name", "New name of the subnet group.");

		setDisplayGroup(Locale.KOREAN, "샘플");
		setDisplayName(Locale.KOREAN, "네트워크 대역 그룹 생성");
		setDescription(Locale.KOREAN, "로그프레소 서버에 새 네트워크 대역 그룹을 생성합니다.");
		setOption("name", REQUIRED, Locale.KOREAN, "이름", "새 네트워크 대역 그룹 이름");
	}

	@Override
	public String getCommandName() {
		return "sample-create-subnet-group";
	}

	@Override
	protected QueryCommand parse(QueryContext context, SampleParams params) {
		if (params.getName() == null)
			throw new QueryParseException(ERR_NAME_REQUIRED, -1);

		return new SampleCreateSubnetGroupCommand(params);
	}
}
