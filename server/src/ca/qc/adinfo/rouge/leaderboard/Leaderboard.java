package ca.qc.adinfo.rouge.leaderboard;

import java.util.Collection;
import java.util.LinkedList;

public class Leaderboard {
	
	// TODO Add Persistence to Scores
	 
	private LinkedList<Score> scores;
	private String key;
	
	public Leaderboard(String key) {
		
		this.key = key;
		this.scores = new LinkedList<Score>();
	}
	
	
	public void addScore(Score score) {
		
		synchronized (this.scores) {
			this.scores.add(score);
		}
	}
	
	public Collection<Score> getScore() {
		
		Collection<Score> toReturn = new LinkedList<Score>();
		
		synchronized (this.scores) {
			toReturn.addAll(this.scores);
		}
		
		return toReturn;
	}

	public String getKey() {
	
		return this.key;
	}
}
