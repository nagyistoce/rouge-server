package ca.qc.adinfo.rouge.server.highscore;

import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;

public class GetHighScore extends RougeCommand {

	public GetHighScore(String key) {
		super(key);
		
	}

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {
		
		
	}

}
