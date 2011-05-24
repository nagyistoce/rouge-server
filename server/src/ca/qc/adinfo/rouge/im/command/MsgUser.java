package ca.qc.adinfo.rouge.im.command;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.server.core.SessionContext;
import ca.qc.adinfo.rouge.user.User;
import ca.qc.adinfo.rouge.user.UserManager;

public class MsgUser extends RougeCommand {

	@Override
	public void execute(RougeObject data, SessionContext session, User user) {

		UserManager userManager = (UserManager)RougeServer.getInstance().getModule("user.manager");

		long targetId = data.getLong("target");
		String message = data.getString("msg");

		User targetUser = userManager.getUserById(targetId);

		if (targetUser == null) {
			
			sendFailure(session);

		} else {

			RougeObject rougeObject = new RougeObject();
			rougeObject.putString("msg", message);

			targetUser.getSessionContext().send("im.recv", rougeObject);
			
			sendSuccess(session);
		}
	}

}
