package ca.qc.adinfo.rouge.data;

public class RougeVariable {

	private String key;
	private RougeObject value;
	private long version;
	
	public RougeVariable(String key, RougeObject value) {
		super();
		this.key = key;
		this.value = value;
		this.version = 0;
	}
	
	public RougeVariable(String key, RougeObject value, long version) {
		super();
		this.key = key;
		this.value = value;
		this.version = version;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public RougeObject getValue() {
		return value;
	}

	public void setValue(RougeObject value) {
		this.value = value;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
	
	public void incVersion() {
		this.version++;
	}
	
	
	
}
