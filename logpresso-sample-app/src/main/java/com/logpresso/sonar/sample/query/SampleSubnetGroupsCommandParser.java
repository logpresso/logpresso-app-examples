package com.logpresso.sonar.sample.query;

import java.util.Locale;

import org.araqne.log.api.ValueType;
import org.araqne.logdb.QueryCommand;
import org.araqne.logdb.QueryContext;

public class SampleSubnetGroupsCommandParser extends SampleQueryCommandParser {

	public SampleSubnetGroupsCommandParser() {
		setDescription(Locale.ENGLISH, "Get subnet groups from the Logpresso server.");
		setDescription(Locale.KOREAN, "�α������� ������ ��Ʈ��ũ �뿪 �׷� ����� ��ȸ�մϴ�.");

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
		setOutput("name", ValueType.STRING, Locale.KOREAN, "�̸�", "");
		setOutput("description", ValueType.STRING, Locale.KOREAN, "����", "");
		setOutput("subnet_count", ValueType.INT, Locale.KOREAN, "�׸� ��", "��Ʈ��ũ �뿪 �׷쿡 ���� �׸� ��");
		setOutput("user_name", ValueType.STRING, Locale.KOREAN, "���� �̸�", "");
		setOutput("user_guid", ValueType.STRING, Locale.KOREAN, "���� GUID", "");
		setOutput("created", ValueType.DATE, Locale.KOREAN, "�����Ͻ�", "");
		setOutput("updated", ValueType.DATE, Locale.KOREAN, "�����Ͻ�", "");
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
