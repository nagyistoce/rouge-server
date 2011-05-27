package ca.qc.adinfo.rouge.handler;

import ca.qc.adinfo.rouge.RougeDriver;
import ca.qc.adinfo.rouge.data.RougeObject;

public class ImMessageHandler extends CoreHandler {

	public ImMessageHandler(RougeDriver driver) {
		super(driver);
	}

	@Override
	public void handle(String command, RougeObject payload) {

		if (this.driver.listener != null) {
			this.driver.listener.onIM(
					payload.getString("from"), 
					payload.getString("msg"));
		}		
	}
	
	

}
