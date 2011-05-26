
import java.util.Collection;

import ca.qc.adinfo.rouge.RougeConnectionFailure;
import ca.qc.adinfo.rouge.RougeDriver;
import ca.qc.adinfo.rouge.RougeListener;
import ca.qc.adinfo.rouge.data.RougeAchievement;
import ca.qc.adinfo.rouge.data.RougeLeaderboard;
import ca.qc.adinfo.rouge.data.RougeMail;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.data.RougeVariable;

public class DemoRouge implements RougeListener {

	private final static String HOST = "127.0.0.1";
	private final static int PORT = 6611;
	private final static boolean USE_BENCODE = false;
	
	private RougeDriver driver;
	
	public DemoRouge() {
		
		System.out.println("Starting test program ...");
		this.driver = new RougeDriver(HOST, PORT, this, USE_BENCODE);
		System.out.println("Connecting ...");
		
		try {
			this.driver.connect();
		} catch (RougeConnectionFailure e) {
			System.exit(-1);
		}		
		
		while(true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// Nothing to do
			}
		}
	}
	
	@Override
	public void onOtherMessage(String command, RougeObject payload) {
		
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
	
	@Override
	public void onError(String command, RougeObject error) {
		
		System.err.println("Error on " + command + " " + error.toJSON().toString());
		
	}

	public static void main(String[] args) {

	}

	@Override
	public void onLogin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserCreated(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomCreated(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomDestroyed(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomJoined(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomLeft(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomSaid(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomSay(String name, String from, RougeObject message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVariableGot(RougeVariable variable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVariableSet(String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVariableSubcribed(String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVariableUnsubscribed(String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVariableUpdated(String key, RougeVariable variable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPersistentVariableGot(RougeVariable variable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPersistentVariableSet(String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onIM(String from, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSentMail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetAllMail(Collection<RougeMail> mails) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetUnreadMail(Collection<RougeMail> mails) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMailIsMarkedRead() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeleteMail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetLeaderboard(RougeLeaderboard leaderboard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetLeaderboards(Collection<RougeLeaderboard> leaderboard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSubmitScore(String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGotAchievements(Collection<RougeAchievement> achievements) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdateAchivementProgress(String key) {
		// TODO Auto-generated method stub
		
	}



}
