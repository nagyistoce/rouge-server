package ca.qc.adinfo.rouge;

import ca.qc.adinfo.rouge.data.RougeObject;

public interface RougeHandler {

	public void handle(String command, RougeObject payload);
	
	
	
}
