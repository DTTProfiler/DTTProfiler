package computeClasses;

import runTTProfLite.RunTTProfLite;

public class GetDistinctClasses {
	
	GetDistinctClasses(){
		String filterOntoConcep = "";
		if(RunTTProfLite.triplestore.getOntoPref()!=null)
			filterOntoConcep = "filter( regex(str(?concept), \"Concept\"))";
		
		String queryStr =""
				+ "SELECT * "
				+ RunTTProfLite.triplestore.getGraph()
				+ "	WHERE {"
				+ "	  	{"
				+ "			SELECT DISTINCT ?concept {"
				+ "				?s a ?concept ."
				+				filterOntoConcep
			//	+ "				filter(!isBlank(?concept))"
				+ "			}"
				+ "		}"
				+ "	}";
		
		try {
			new RunQueryConcept(RunTTProfLite.triplestore, queryStr)  {
			}.execute();
		} catch (InterruptedException e) {}	

		System.out.println("LDIOPBSF : "+GetClassesAndPredicates.allClasses.size());//+"\t"+GetClassesAndPredicates.allClasses);
		//System.exit(0);
	}
}
