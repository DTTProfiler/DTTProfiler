package graphBuilder;

public class ChildLabel {
	String name;
	String prefix;
	String uri;
	String query_String;
	String endpoint;
	
	public ChildLabel(String name, String prefix, String uri, String query_String, String endpoint) {
		this.name = "\""+name+"\"";
		this.prefix = "\""+prefix+"\"";
		this.uri = "\""+uri+"\"";
		this.query_String = "\""+query_String+"\"";
		this.endpoint = "\""+endpoint+"\"";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getQuery_String() {
		return query_String;
	}

	public void setQuery_String(String query_String) {
		this.query_String = query_String;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	@Override
	public String toString() {
		return "{\"name\":" + this.name + ", \"prefix\":" + this.prefix + ", \"uri\":" + this.uri + ", \"query_String\":" + this.query_String
				+ ", \"endpoint\":" + this.endpoint + "}";
	}
	
	
}
