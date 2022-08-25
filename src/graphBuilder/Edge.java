package graphBuilder;

import java.util.ArrayList;
import java.util.List;

import runTTProfLite.RunTTProfLite;

public class Edge {
	String source;
	String target;
	String value;
	String prefix;
	String uri;
	Integer stat;
	List<String> subset_of_source;
	List<String> subset_of_target;
	
	public Edge(String source, String target, String value, String prefix, String uri, Integer stat,
			List<String> subset_of_source, List<String> subset_of_target) {
		this.source = "\""+source+"\"";
		this.target = "\""+target+"\"";
		this.value = "\""+value+"\"";
		this.prefix = "\""+prefix+"\"";
		this.uri = "\""+uri+"\"";
		this.stat = stat;
		this.subset_of_source = new ArrayList<String>();
		for(String elem:subset_of_source) {
			String link = elem.split(RunTTProfLite.separator)[0];
			String[] name = link.split("/");
			if(!this.subset_of_source.contains(name[name.length-1]))
				this.subset_of_source.add(name[name.length-1]);
		}
		this.subset_of_target = new ArrayList<String>();
		for(String elem:subset_of_target) {
			String link = elem.split(RunTTProfLite.separator)[0];
			String[] name = link.split("/");
			if(!this.subset_of_target.contains(name[name.length-1]))
				this.subset_of_target.add(name[name.length-1]);
		}
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public Integer getStat() {
		return stat;
	}

	public void setStat(Integer stat) {
		this.stat = stat;
	}

	public List<String> getSubset_of_source() {
		return subset_of_source;
	}

	public void setSubset_of_source(List<String> subset_of_source) {
		this.subset_of_source = subset_of_source;
	}

	public List<String> getSubset_of_target() {
		return subset_of_target;
	}

	public void setSubset_of_target(List<String> subset_of_target) {
		this.subset_of_target = subset_of_target;
	}

	@Override
	public String toString() {
		String sbs="[";
		for(String e:subset_of_source) {
			if(sbs.length()>1)
				sbs+= ", \""+e+"\"";
			else
				sbs+= "\""+e+"\"";
		}
		sbs += "]";
		String sbt="[";
		for(String e:subset_of_target) {
			if(sbt.length()>1)
				sbt+= ", \""+e+"\"";
			else
				sbt+= "\""+e+"\"";
		}
		sbt += "]";
		return "{\"source\":" + source + ", \"target\":" + target + ", \"value\":" + value + ", \"prefix\":" + prefix + ", \"uri\":"
				+ uri + ", \"stat\":" + stat + ", \"subset_of_source\":" + sbs + ", \"subset_of_target\":"
				+ sbt + "}\n";
	}
	
	
}
