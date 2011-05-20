package ca.qc.adinfo.rouge.leaderboard;

import java.util.HashMap;
import java.util.Properties;

import ca.qc.adinfo.rouge.RougeServer;
import ca.qc.adinfo.rouge.module.RougeModule;

public class LeaderboardManager extends RougeModule {

	private HashMap<String, Leaderboard> leaderboards;
	
	private int maxCapacityBoard;
	
	public LeaderboardManager() {
		
		//Properties props = RougeServer.getInstance().getServerProperties();
		
		//this.maxCapacityBoard = Integer.parseInt(props.getProperty(arg0));
		this.leaderboards = new HashMap<String, Leaderboard>();
		
	}
	
	public Leaderboard getLeaderboards(String name) {
		
		Leaderboard leaderboard = null;
		
		synchronized (this.leaderboards) {
			leaderboard = this.leaderboards.get(name);
		}
		
		if(leaderboard == null) {
			leaderboard = new Leaderboard(maxCapacityBoard);
			
			synchronized (this.leaderboards) {
				this.leaderboards.put(name, leaderboard);
			}
		}
		
		return leaderboard;
	}
	
	@Override
	public void tick(long time) {
		
	}

}
