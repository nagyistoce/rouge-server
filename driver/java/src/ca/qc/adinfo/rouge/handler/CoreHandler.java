package ca.qc.adinfo.rouge.handler;

import ca.qc.adinfo.rouge.RougeDriver;
import ca.qc.adinfo.rouge.RougeHandler;

public abstract class CoreHandler implements RougeHandler {

	protected RougeDriver driver;

	public CoreHandler(RougeDriver driver) {
		super();
		this.driver = driver;
	}
	
}
