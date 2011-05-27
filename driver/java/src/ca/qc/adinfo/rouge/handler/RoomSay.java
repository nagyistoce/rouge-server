package ca.qc.adinfo.rouge.handler;

import ca.qc.adinfo.rouge.RougeDriver;
import ca.qc.adinfo.rouge.data.RougeObject;

public class RoomSay extends CoreHandler {

	public RoomSay(RougeDriver driver) {
		super(driver);
	}

	@Override
	public void handle(String command, RougeObject payload) {

		if (this.driver.listener != null) {
			this.driver.listener.onRoomSay(
					payload.getString("name"), 
					payload.getString("from"), 
					payload.getRougeObject("msg"));
		}		
	}
	
	

}
