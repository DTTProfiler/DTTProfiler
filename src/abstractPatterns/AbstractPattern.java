package abstractPatterns;

/*
 * This class materializes an abstract pattern ((C,P,D),w) where:
 * C is the list of subjects
 * P is the predicate of the pattern
 * D is the list of objects
 * w is the weight (statistic) that contains the number of instances of that pattern in the ABox 
 * */

import java.util.List;

public class AbstractPattern {
	private List<String> listOfSubjectConcept; // list of subjects
	private String predicate; // predicate of the pattern
	private List<String> listOfObjectConcept; // the list of objects
	private Integer weight; // statistic that contains the number of instances
	
	public AbstractPattern(List<String> listOfSubjectConcept, String string, List<String> listOfObjectConcept,
			Integer weight) {

		this.listOfSubjectConcept = listOfSubjectConcept;
		this.predicate = string;
		this.listOfObjectConcept = listOfObjectConcept;
		this.weight = weight;
	}

	public List<String> getListOfSubjectConcept() {
		return listOfSubjectConcept;
	}

	public void setListOfSubjectConcept(List<String> listOfSubjectConcept) {
		this.listOfSubjectConcept = listOfSubjectConcept;
	}

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public List<String> getListOfObjectConcept() {
		return listOfObjectConcept;
	}

	public void setListOfObjectConcept(List<String> listOfObjectConcept) {
		this.listOfObjectConcept = listOfObjectConcept;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "AbstractPattern [listOfSubjectConcept=" + this.listOfSubjectConcept + ", predicate=" + this.predicate
				+ ", listOfObjectConcept=" + this.listOfObjectConcept + ", weight=" + this.weight + "]";
	}
	
	
	
}
