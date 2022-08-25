package patterns;

import java.util.List;

import abstractPatterns.AbstractPattern;

public class ProfPattern {
	private List<String> maxNodeSubject;
	private AbstractPattern maxNode;
	private List<String> maxNodeObject;
	
	
	public ProfPattern(List<String> list, AbstractPattern maxNode, List<String> list2) {
		//super();
		this.maxNodeSubject = list;
		this.maxNode = new AbstractPattern(maxNode.getListOfSubjectConcept(), maxNode.getPredicate(), maxNode.getListOfObjectConcept(), maxNode.getWeight());
		this.maxNodeObject = list2;
	}


	public List<String> getMaxNodeSubject() {
		return maxNodeSubject;
	}

	public void setMaxNodeSubject(List<String> maxNodeSubject) {
		this.maxNodeSubject = maxNodeSubject;
	}
	public AbstractPattern getMaxNode() {
		return maxNode;
	}
	public void setMaxNode(AbstractPattern maxNode) {
		this.maxNode = maxNode;
	}
	public List<String> getMaxNodeObject() {
		return maxNodeObject;
	}
	public void setMaxNodeObject(List<String> maxNodeObject) {
		this.maxNodeObject = maxNodeObject;
	}
	@Override
	public String toString() {
		return "ProfPattern [maxNodeSubject=" + maxNodeSubject + ", maxNode=" + maxNode + ", maxNodeObject="
				+ maxNodeObject + "]";
	}
	
	
}
