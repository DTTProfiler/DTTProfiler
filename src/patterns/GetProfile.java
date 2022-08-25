package patterns;

import java.util.List;

import abstractPatterns.AbstractPattern;

public class GetProfile {
	
	public GetProfile() {
		for(AbstractPattern absPat:GetPatterns.setOfAbstractPatterns.keySet()) {

			//System.out.println("LDIOPBSF "+absPat);
			GetPatterns.finalProfil.add(new ProfPattern(MaxNodeOf(absPat.getListOfSubjectConcept()), absPat, MaxNodeOf(absPat.getListOfObjectConcept())));
		}
	}
	
	List<String> MaxNodeOf(List<String> node){
		for(List<String> maxNode:GetPatterns.setOfMaxNodes) {
			if(maxNode.containsAll(node))
				return maxNode;
		}
		//System.out.println(node);
		//System.exit(0);
		return null;
	}
}
