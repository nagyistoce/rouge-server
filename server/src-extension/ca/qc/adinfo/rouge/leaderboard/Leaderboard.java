package ca.qc.adinfo.rouge.leaderboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

public class Leaderboard {
	
	private TreeSet<Score> scores;
	private int maxCapacity;
	
	public Leaderboard(int maxCapacity) {
		
		this.scores = new TreeSet<Score>();
		this.maxCapacity = maxCapacity;
	}
	
	
	public void addScore(Score score) {
		
		synchronized (this.scores) {
			this.scores.add(score);
			
			if (this.scores.size() > maxCapacity) {
				this.scores.remove(this.scores.last());
			}
		}
	}
	
	public Collection<Score> getScore() {
		
		Collection<Score> toReturn = new ArrayList<Score>();
		
		synchronized (this.scores) {
			toReturn.addAll(this.scores);
		}
		
		return toReturn;
	}

}
