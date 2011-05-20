package ca.qc.adinfo.rouge.leaderboard;

import ca.qc.adinfo.rouge.user.User;

public class Score implements Comparable {
	
	private long score;
	private User user;
	
	public Score(User user, long score) {
		this.user = user;
		this.score = score;
	}

	public long getScore() {
		return score;
	}

	public User getUser() {
		return user;
	}

	@Override
	public int compareTo(Object o) {
		
		Score other = (Score)o;
		
		if (this.score > other.score) return 1;
		if (this.score == other.score) return 0;
		return -1;
	}

}
