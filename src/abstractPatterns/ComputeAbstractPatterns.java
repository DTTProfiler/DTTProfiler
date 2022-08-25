package abstractPatterns;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import patterns.GetPatterns;
import patterns.Pattern;
import runTTProfLite.RunTTProfLite;


public class ComputeAbstractPatterns {
	public static int testNumber = 0;
	public ComputeAbstractPatterns(Set<Pattern> set) {
		String queryStr ="";
		List<String> setOfEqSubjClass;
		List<String> setOfEqObjClass;
		int i=0;
		List<String> distinctPattern = new ArrayList<String>();
		for(Pattern pattern1:set) {

			setOfEqSubjClass = new ArrayList<String>();
			setOfEqSubjClass.add(pattern1.getSubjectConcept());
			setOfEqObjClass = new ArrayList<String>();
			setOfEqObjClass.addAll(pattern1.getObjectConcept());
			int j=0;
			for(Pattern pattern2:set) {
				if(j>i && pattern1.getPredicate().compareTo(pattern2.getPredicate())==0 && 
					pattern1.getWeight()==pattern2.getWeight() &&
					pattern1.getSubjectConcept().compareTo(pattern2.getSubjectConcept())!=0 &&
					pattern1.getObjectConcept().toString().compareTo(pattern2.getObjectConcept().toString())==0){

					if(!pattern1.getSubjectConcept().contains(RunTTProfLite.separator) &&
						!pattern2.getSubjectConcept().contains(RunTTProfLite.separator)) {
						queryStr = ""
								+ "SELECT * "
								+ RunTTProfLite.triplestore.getGraph()
								+ "	WHERE {"
								+ "	  	{"
								+ "			SELECT (count(*) AS ?countPred){"
								+ "				?s <"+pattern1.getPredicate()+"> ?o ."
								+ "				?s a <"+pattern1.getSubjectConcept()+"> ."
								+ "				?s a <"+pattern2.getSubjectConcept()+"> ."
								+ "			}"
								+ "		}"
								+ "	}";
						
						
						ComputeAbstractPatterns.testNumber = 0;
						try {
							new RunQuery(RunTTProfLite.triplestore, queryStr)  {
							}.execute();
						} catch (InterruptedException e) {}	
						if(ComputeAbstractPatterns.testNumber==pattern1.getWeight()) {
							setOfEqSubjClass.addAll(pattern2.getObjectConcept());
						}
					}
					
				}else
					if(j>i && pattern1.getPredicate().compareTo(pattern2.getPredicate())==0 && 
					pattern1.getWeight()==pattern2.getWeight() &&
					pattern1.getSubjectConcept().compareTo(pattern2.getSubjectConcept())==0 &&
					pattern1.getObjectConcept().toString().compareTo(pattern2.getObjectConcept().toString())!=0){

					if(!pattern1.getObjectConcept().get(0).contains(RunTTProfLite.separator) &&
						!pattern2.getObjectConcept().get(0).contains(RunTTProfLite.separator) &&
						pattern1.getObjectConcept().get(0).contains("http") &&
						pattern2.getObjectConcept().get(0).contains("http")) {
						queryStr = ""
								+ "SELECT * "
								+ RunTTProfLite.triplestore.getGraph()
								+ "	WHERE {"
								+ "	  	{"
								+ "			SELECT (count(*) AS ?countPred){"
								+ "				?s <"+pattern1.getPredicate()+"> ?o ."
								+ "				?o a <"+pattern1.getObjectConcept().get(0)+"> ."
								+ "				?o a <"+pattern2.getObjectConcept().get(0)+"> ."
								+ "			}"
								+ "		}"
								+ "	}";
						ComputeAbstractPatterns.testNumber = 0;
						try {
							new RunQuery(RunTTProfLite.triplestore, queryStr)  {
							}.execute();
						} catch (InterruptedException e) {}	
	
						if(ComputeAbstractPatterns.testNumber ==pattern1.getWeight()) {
							setOfEqObjClass.addAll(pattern2.getObjectConcept());
						}
					}
					
				}
				j ++;
			}
			i ++;
			AbstractPattern patt = new AbstractPattern(setOfEqSubjClass, pattern1.getPredicate() ,setOfEqObjClass, pattern1.getWeight());
			if(!distinctPattern.contains(patt.toString())) {
				GetPatterns.setOfAbstractPatterns.put(patt, null);
				distinctPattern.add(patt.toString());
			}
			testAndSetMaxNode(setOfEqSubjClass, GetPatterns.setOfMaxNodes);
			testAndSetMaxNode(setOfEqObjClass, GetPatterns.setOfMaxNodes);
		}
	}
	
	void testAndSetMaxNode(List<String> setOfClass, HashSet<List<String>> setOfMaxNodes) {
		HashSet<List<String>> newSetOfMaxNodes = new HashSet<List<String>>(setOfMaxNodes);
		HashSet<String> unionSetOfClass = new HashSet<String>();
		unionSetOfClass.addAll(setOfClass);
		if(!setOfMaxNodes.contains(setOfClass)) {
			for(List<String> elem:newSetOfMaxNodes) {
				if(elem.containsAll(setOfClass))
					return;
				if(setOfClass.containsAll(elem))
					setOfMaxNodes.remove(elem);
				else {
					Set<String> intersection = new HashSet<String>(setOfClass);
					intersection.retainAll(elem);
					if (intersection.size()!=0){
						unionSetOfClass.addAll(elem);
						setOfMaxNodes.remove(elem);
					}					
				}
			}
			setOfMaxNodes.add(new ArrayList<String>(unionSetOfClass));
		}
	}
}
