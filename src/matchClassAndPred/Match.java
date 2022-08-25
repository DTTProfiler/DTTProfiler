package matchClassAndPred;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import patterns.GetPatterns;
import runTTProfLite.RunTTProfLite;



public class Match {
	
	public Match(Map<String, List<String>> setOfConceptInit, Map<String, List<String>> setOfPredicate) {
		String queryStr ="";
		Map<String, List<String>> setOfConcept = new HashMap<String, List<String>>(setOfConceptInit);
		String filterOntoConcep = "";
		if(RunTTProfLite.triplestore.getOntoPref()!=null)
			filterOntoConcep = "filter(regex(str(?D),"+RunTTProfLite.triplestore.getOntoPref()+")  ||"
					+ " regex(str(?D), \"NamedIndividual\") ||"
					+ " regex(str(?D), \"http://www.w3.org/2004/02/skos/core#Concept\"))";
		
		for(String conceptSubject:setOfConcept.keySet())
				for(String predicate:setOfConcept.get(conceptSubject)) {
					queryStr = "SELECT * "
						+ RunTTProfLite.triplestore.getGraph()
						+ "	WHERE {"
						+ "	  	{"
						+ "			SELECT DISTINCT ?C ?P ?D  {"
						+ "				?subj a <"+conceptSubject+"> ."
						+ "				?obj a ?D ."
						+ "				?subj <"+predicate+"> ?obj ."
						+ "				BIND(<"+conceptSubject+"> AS ?C)"
						+ "				BIND(<"+predicate+"> AS ?P)"
						+ "				FILTER(!isBlank(?obj))"
						+ 				filterOntoConcep
						+ "			}"
						+ "		}"
						+ "	}";
		
					try {
						new RunQuery(RunTTProfLite.triplestore, queryStr)  {
						}.execute();
					} catch (InterruptedException e) {}	
				}

		System.out.println("\nCC : "+GetPatterns.setOfConcept.toString());
	}
}
