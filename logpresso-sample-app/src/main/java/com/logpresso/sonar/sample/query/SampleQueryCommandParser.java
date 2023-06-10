package com.logpresso.sonar.sample.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.araqne.logdb.QueryCommand;
import org.araqne.logdb.QueryContext;
import org.araqne.logdb.QueryErrorMessage;
import org.logpresso.api.profile.query.ConnectProfileParams;
import org.logpresso.api.profile.query.ConnectProfileQueryCommandParser;

public abstract class SampleQueryCommandParser extends ConnectProfileQueryCommandParser {

	protected static final String ERR_SERVICE_UNAVAILABLE = "204000";
	protected static final String ERR_PROFILE_REQUIRED = "204001";
	protected static final String ERR_NAME_REQUIRED = "204002";

	public SampleQueryCommandParser() {
		super("sample", ERR_SERVICE_UNAVAILABLE, ERR_PROFILE_REQUIRED);
	}

	protected List<String> getSupportedOptions() {
		return new ArrayList<String>(getCommandHelp().getOptions().keySet());
	}

	protected abstract QueryCommand parse(QueryContext context, SampleParams params);

	@Override
	protected ConnectProfileParams parseParams(QueryContext context, Map<String, String> opts) {
		SampleParams params = new SampleParams();
		params.setName(opts.get("name"));
		return params;
	}

	@Override
	protected QueryCommand parse(QueryContext context, ConnectProfileParams params, String commandString) {
		return parse(context, (SampleParams) params);
	}

	@Override
	public Map<String, QueryErrorMessage> getErrorMessages() {
		Map<String, QueryErrorMessage> errors = new HashMap<>();
		errors.put(ERR_SERVICE_UNAVAILABLE, newMsg("No available sample profile found.", "사용 가능한 샘플 프로파일이 없습니다."));
		errors.put(ERR_PROFILE_REQUIRED, newMsg("Specify valid sample profile.", "샘플 프로파일 이름을 입력해주세요."));
		errors.put(ERR_NAME_REQUIRED, newMsg("Specify name option in the sample-create-subnet-group command.",
				"sample-create-subnet-group 명령어에 name 옵션을 지정하세요."));

		return errors;
	}

	private QueryErrorMessage newMsg(String en, String ko) {
		return new QueryErrorMessage(en, ko);
	}
}
