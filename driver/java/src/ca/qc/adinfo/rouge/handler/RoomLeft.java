package ca.qc.adinfo.rouge.handler;

import ca.qc.adinfo.rouge.RougeDriver;
import ca.qc.adinfo.rouge.data.RougeObject;

public class RoomLeft extends CoreHandler {

	public RoomLeft(RougeDriver driver) {
		super(driver);
	}

	@Override
	public void handle(String command, RougeObject payload) {

		if (this.driver.listener != null) {
			this.driver.listener.onRoomLeft(payload.getString("name"));
		}		
	}
	
	

}
