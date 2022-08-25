package computeClasses;

import java.util.HashSet;
import java.util.Set;

import patterns.GetPatterns;
import runTTProfLite.RunTTProfLite;



public class GetClassesAndPredicates {
	public static Set<String> allClasses = new HashSet<String>();
	public GetClassesAndPredicates() {

		new GetDistinctClasses();
		//System.out.println(allClasses+"\n"+allClasses.size());
		//System.exit(0);
		String filterOntoPred = "";
		if(RunTTProfLite.triplestore.getOntoPref()!=null) {
			filterOntoPred = "filter(regex(str(?predicate),"+RunTTProfLite.triplestore.getOntoPref()+"))";
		}
		for(String concept:allClasses) {
			String queryStr =""
					+ "SELECT * "
					+ RunTTProfLite.triplestore.getGraph()
					+ "	WHERE {"
					+ "	  	{"
					+ "			SELECT DISTINCT ?concept ?predicate {"
					+ "				?s a <"+concept+"> ."
					+ "				?s ?predicate ?o ."
					+				filterOntoPred
					+ "				filter(!isBlank(?o))"
					+ "				filter(!isLiteral(?o))"
					+ "				BIND(<"+concept+"> as ?concept)"
					+ "			}"
					+ "		}"
					+ "	}";
			
			try {
				new RunQuery(RunTTProfLite.triplestore, queryStr)  {
				}.execute();
			} catch (InterruptedException e) {}	
		}
	}
}
