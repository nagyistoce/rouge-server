
import ca.qc.adinfo.rouge.RougeDriver;
import ca.qc.adinfo.rouge.RougeListener;
import ca.qc.adinfo.rouge.data.RougeObject;

public class DemoRouge implements RougeListener {

	private final static String HOST = "127.0.0.1";
	private final static int PORT = 6611;
	private final static boolean USE_BENCODE = false;
	
	private RougeDriver driver;
	
	public DemoRouge() {
		
		System.out.println("Starting test program ...");
		this.driver = new RougeDriver(HOST, PORT, this, USE_BENCODE);
		System.out.println("Connecting ...");
		this.driver.connect();		
		
		while(true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onMessage(String command, RougeObject payload) {
		
		System.out.println(command + " " + payload.toJSON().toString());
		
		if (command.equals("login")) {
			
			RougeObject newPayload = new RougeObject();
			newPayload.putString("name", "testRoom");
			this.driver.send("room.create", newPayload);
			
			newPayload.putString("message", "hello");
			this.driver.send("room.say", newPayload);
			
		}
	}

	@Override
	public void onConnect() {
		
		System.out.println("Connected ...");
		
		RougeObject payload = new RougeObject();
		payload.putString("username", "bob" + System.currentTimeMillis());
		payload.putString("password", "password");
				
		this.driver.send("login", payload);
	}

	@Override
	public void onDisconnect() {
		
		System.out.println("Disconnected ...");
	}	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
