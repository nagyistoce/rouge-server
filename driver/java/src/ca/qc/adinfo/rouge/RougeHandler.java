package ca.qc.adinfo.rouge;

import ca.qc.adinfo.rouge.data.RougeObject;

public class RougeHandler {

	public void handle(String command, RougeObject payload, RougeListener rougeListener) {
		
		// Nothing to do without a listener
		if (rougeListener == null) return;
		
		
		if (payload.hasKey("ret")) {
			boolean ret = payload.getBoolean("ret");
			if (ret == false) {
				rougeListener.onError(command, payload);
			}			
		}
				
		if (command.equals("login")) {
			rougeListener.onLogin();
		} else if(command.equals("user.create")) {
			rougeListener.onUserCreated(payload.getLong("id"));
		} else if(command.equals("im.msg")) {
			rougeListener.onIM(payload.getString("from"), payload.getString("msg"));
		}
		else {
			rougeListener.onOtherMessage(command, payload);
		}		
	}
	
	
	
}
