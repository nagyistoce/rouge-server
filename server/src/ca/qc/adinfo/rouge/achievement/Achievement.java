package ca.qc.adinfo.rouge.achievement;


public class Achievement {
	
	private String key;
	private double progress;
	private double max;
	
	public Achievement(String key, float max, float progress) {
		
		this.key = key;
		this.max = max;
		this.progress = this.progress;
	}
	
	public String getKey() {
		return key;
	}
	
	public double getProgress() {
		return progress;
	}
	
	public void updateProgress(double progress) {
		this.progress = progress;
	}
	
	public double getMax() {
		return max;
	}
	
	

}
