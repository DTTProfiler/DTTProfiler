package computeStats;

import java.util.Set;

import patterns.GetPatterns;
import patterns.Pattern;
import runTTProfLite.RunTTProfLite;



public class GetPatternStats{
	public static int weight;
	public GetPatternStats(Set<Pattern> set) {
		String queryStr ="";
		for(Pattern pattern:set) {
			if(!pattern.getSubjectConcept().contains(RunTTProfLite.separator) && !pattern.getObjectConcept().get(0).contains(RunTTProfLite.separator)) {
				weight = 0;
				queryStr = ""
				+ "SELECT * "
				+ RunTTProfLite.triplestore.getGraph()
				+ "	WHERE {"
				+ "	  	{"
				+ "			SELECT (count(*) AS ?countPred){"
				+ "				?s <"+pattern.getPredicate()+"> ?o ."
				+ "				?s a <"+pattern.getSubjectConcept()+"> ."
				+ "				?o a <"+pattern.getObjectConcept().get(0)+"> ."
				+ "			}"
				+ "		}"
				+ "	}";
				try {
					new RunQuery(RunTTProfLite.triplestore, queryStr)  {
					}.execute();
				} catch (InterruptedException e) {}	
				if(weight!=0) {
					pattern.setWeight(weight);
					GetPatterns.setOfPatterns.put(pattern, GetPatterns.setOfPatterns.get(pattern));
				}
			}
		}
	}
}
