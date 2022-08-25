package patterns;

import java.util.ArrayList;
import java.util.List;

public class Pattern {
	private String subjectConcept;
	private String predicate;
	private List<String> objectConcept;
	private Integer weight;
	
	public Pattern(String subjectConcept, String predicate, List<String> objectConcept, Integer weight){
		this.subjectConcept = subjectConcept;
		this.predicate = predicate;
		this.objectConcept = objectConcept;
		this.weight = weight;
	}

	public Pattern(Pattern pattern) {
		this.subjectConcept = pattern.getSubjectConcept();
		this.predicate = pattern.getPredicate();
		this.objectConcept = pattern.getObjectConcept();
		this.weight = pattern.getWeight();
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getSubjectConcept() {
		return subjectConcept;
	}

	public void setSubjectConcept(String subjectConcept) {
		this.subjectConcept = subjectConcept;
	}

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public List<String> getObjectConcept() {
		return objectConcept;
	}

	public void setObjectConcept(List<String> objectConcept) {
		this.objectConcept = new ArrayList<String>();
		this.objectConcept.addAll(objectConcept);
	}
	

	@Override
	public String toString() {
		return "Pattern [subjectConcept=" + subjectConcept + ", predicate=" + predicate + ", objectConcept="
				+ objectConcept + ", weight=" + weight + "]";
	}
	
}
