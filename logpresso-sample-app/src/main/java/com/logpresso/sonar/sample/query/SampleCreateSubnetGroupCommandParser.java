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

		setDisplayGroup(Locale.KOREAN, "����");
		setDisplayName(Locale.KOREAN, "��Ʈ��ũ �뿪 �׷� ����");
		setDescription(Locale.KOREAN, "�α������� ������ �� ��Ʈ��ũ �뿪 �׷��� �����մϴ�.");
		setOption("name", REQUIRED, Locale.KOREAN, "�̸�", "�� ��Ʈ��ũ �뿪 �׷� �̸�");
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
