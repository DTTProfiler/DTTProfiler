package graphBuilder;

public class DataProperties {
	String name;
	Integer count;
	String domain;
	String range;
	String uri;
	
	public DataProperties(String name, Integer count, String domain, String range, String uri) {
		this.name = "\""+name+"\"";
		this.count = count;
		this.domain = "\""+domain+"\"";
		this.range = "\""+range+"\"";
		this.uri = "\""+uri+"\"";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		return "{\"name\":" + name + ", \"count\":" + count + ", \"domain\":" + domain + ", \"range\":" + range
				+ ", \"uri\":" + uri + "}";
	}
		
}
