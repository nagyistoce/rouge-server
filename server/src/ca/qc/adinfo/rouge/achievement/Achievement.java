package ca.qc.adinfo.rouge.achievement;


public class Achievement {
	
	private String key;
	private String name;
	private int pointValue;
	
	private double progress;
	private double total;
	
	
	public Achievement(String key, String name, int pointValue, double total, double progress) {
		
		this.key = key;
		this.name = name;
		this.pointValue = pointValue;
		
		this.total = total;
		this.progress = progress;
	}
	
	public void updateProgress(double progress) {
		this.progress = progress;
	}

	public String getKey() {
		return key;
	}

	public int getPointValue() {
		return pointValue;
	}

	public double getProgress() {
		return progress;
	}

	public double getTotal() {
		return total;
	}
	
	public String getName() {
		return name;
	}
}
