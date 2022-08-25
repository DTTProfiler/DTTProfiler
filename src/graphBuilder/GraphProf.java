package graphBuilder;

import java.util.ArrayList;
import java.util.List;

public class GraphProf {
	List<Node> nodes;
	List<Edge> links;
	
	public GraphProf() {
		this.nodes = new ArrayList<Node>();
		this.links = new ArrayList<Edge>();
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Edge> getLinks() {
		return links;
	}

	public void setLinks(List<Edge> links) {
		this.links = links;
	}
	
	public void addToNodes(Node node) {
		this.nodes.add(node);
	}

	public void addToLinks(Edge edge) {
		this.links.add(edge);
	}

	@Override
	public String toString() {
		return "{\n\"nodes\":\n" + nodes + ",\n \n\"links\":\n" + links + "\n}";
	}
	
	
}
