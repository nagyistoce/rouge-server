package ca.qc.adinfo.rouge.leaderboard;

import ca.qc.adinfo.rouge.user.User;

public class Score {
	
	private long score;
	private long userId;
	
	public Score(long userId, long score) {
		this.userId = userId;
		this.score = score;
	}

	public long getScore() {
		return score;
	}

	public long getUser() {
		return userId;
	}

}
