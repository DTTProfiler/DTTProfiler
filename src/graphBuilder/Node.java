package graphBuilder;

import java.util.ArrayList;
import java.util.List;

public class Node {
	String id;
	String prefix;
	List<ChildLabel> children_labels = new ArrayList<ChildLabel>();;
	List<DataProperties> dataproperties = new ArrayList<DataProperties>();
	String query_String;
	String endpoint;
	
	
	
	public Node(String id, String prefix, List<DataProperties> dataproperties, String query_String, String endpoint) {
		this.id = "\""+id+"\"";
		this.prefix = "\""+prefix+"\"";
		this.dataproperties.addAll(dataproperties);
		this.query_String = query_String;
		this.endpoint = endpoint;
	}

	public Node(String id, String prefix, List<ChildLabel> children_labels, List<DataProperties> dataproperties,
			String query_String, String endpoint) {
		this.id = "\""+id+"\"";
		this.prefix = "\""+prefix+"\"";
		this.children_labels.addAll(children_labels);
		this.dataproperties.addAll(dataproperties);
		this.query_String = "\""+query_String+"\"";
		this.endpoint = "\""+endpoint+"\"";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public List<ChildLabel> getChildren_labels() {
		return children_labels;
	}

	public void setChildren_labels(List<ChildLabel> children_labels) {
		this.children_labels = children_labels;
	}

	public List<DataProperties> getDataproperties() {
		return dataproperties;
	}

	public void setDataproperties(List<DataProperties> dataproperties) {
		this.dataproperties = dataproperties;
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
		String chl = "[]", dp ="[]";
		if(this.children_labels!=null)
			chl = this.children_labels.toString();
		if(this.dataproperties!=null)
			dp =this.dataproperties.toString();
		return "{\"id\":" + this.id + ", \"prefix\":" + this.prefix + ", \"children_labels\":" + chl + ", \"dataproperties\":"
				+ dp + ", \"query_String\":" + this.query_String + ", \"endpoint\":" + this.endpoint + "}\n";
	}
	
	
}
