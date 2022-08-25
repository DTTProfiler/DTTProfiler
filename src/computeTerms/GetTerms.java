package computeTerms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import patterns.GetPatterns;
import patterns.Pattern;
import runTTProfLite.RunTTProfLite;



public class GetTerms{
	public static String  fromThesau = RunTTProfLite.triplestore.getThesau();
	public static HashMap<String, List<String>> terms = new HashMap<String, List<String>>();
	public static HashMap<String, Integer> countAll = new HashMap<String, Integer>();
	public static List<String> termsFromConcept = new ArrayList<String>();
	
	public GetTerms(Set<Pattern> set) {
		String queryStr ="";
		//boolean containsTerm = false;
		if(GetPatterns.hasSkosConcept) {
			//System.out.println(set.toString());
			//System.out.println(GetPatterns.hasSkosConcept+" "+set.size());
	        //System.exit(0);
			for(Pattern pattern:set) {
				if(pattern.getObjectConcept().get(0).contains("http://www.w3.org/2004/02/skos/core#Concept")){
						System.out.println(pattern.getObjectConcept().get(0)+"\t"+pattern.getPredicate());
						queryStr =""
							+ "SELECT *"
							+ RunTTProfLite.triplestore.getGraph() 
							+ "WHERE {"
							+ "      {"
							+ "			SELECT DISTINCT ?term {"
							+ "				?s <"+pattern.getPredicate()+"> ?term ."
							+ "				?s a <"+pattern.getSubjectConcept()+"> ."
							+ "				?term a <"+pattern.getObjectConcept().get(0)+"> ."
							//+ "				"+RunTTProfLite.triplestore.getLang()
							+ "			}"
							+ "      }"
							+ "} ";
						
						try {
							new RunQueryTerms(RunTTProfLite.triplestore, queryStr)  {
							}.execute();
						} catch (InterruptedException e) {}
						/*	
					queryStr = ""
						+ "	SELECT DISTINCT ?term {"
						+ "		?s <"+pattern.getPredicate()+"> ?term ."
						+ "		?s a <"+pattern.getSubjectConcept()+"> ."
						+ "		?term a <"+pattern.getObjectConcept().get(0)+"> ."
						+ "		"+RunTTProfLite.triplestore.getLang()
						+ "	}";
					String queryString = 
			            "SELECT * WHERE { "
			    		+	"    SERVICE <" + RunTTProfLite.triplestore.getEndpoint() + "> { "+
			    					queryStr
						+		"}"
						+"}" ;
			        Query query = QueryFactory.create(queryString) ;
			        try (QueryExecution qexec = QueryExecutionFactory.create(query, ModelFactory.createDefaultModel())) {
			        	if (qexec != null) {
				        	ResultSet resultSet = qexec.execSelect() ;
				        	if (resultSet != null) {
				        		termsFromConcept = new ArrayList<String>();
					            while (resultSet.hasNext()) {
									QuerySolution qs = resultSet.nextSolution();
									if (qs != null) {
										termsFromConcept.add(qs.getResource("term").toString());
										containsTerm = true;
									}								
								}
					            pattern.setObjectConcept(termsFromConcept);
					            pattern.setWeight(pattern.getWeight()*termsFromConcept.size());
								GetPatterns.setOfPatterns.put(pattern, GetPatterns.setOfPatterns.get(pattern));
				        	}
			        	}
			        }catch(Exception e) {}
			        */

					Pattern pattern1 = new Pattern(pattern);
					pattern1.setObjectConcept(termsFromConcept);
					pattern1.setWeight(pattern.getWeight()*termsFromConcept.size());
						
					GetPatterns.setOfPatterns.put(pattern1, GetPatterns.setOfPatterns.get(pattern));
					
				}else if(pattern.getObjectConcept().contains("http://www.w3.org/2004/02/skos")) {
					GetPatterns.setOfPatterns.remove(pattern);
				}
			}

        //System.out.println(set.toString());
        //System.exit(0);
		}
		//if(!containsTerm) {
			//if(!GetPatterns.hasNamedIndividual) {
				for(Pattern pattern:set) {
					terms = new HashMap<String, List<String>>();
					countAll = new HashMap<String, Integer>();
					queryStr =""
						+ "SELECT * "
						+ RunTTProfLite.triplestore.getGraph() 
						+ "WHERE {"
						+ "      {"
						+ "         SELECT ?pred ?resultObj ?subj (COUNT(?pred) AS ?countPred){"
						+ "             VALUES(?fil) {"+fromThesau+"}"
						+ "            	?s a <"+pattern.getSubjectConcept()+"> ; ?pred ?subj ." 
						+ "			   	optional {?subj <http://www.w3.org/2004/02/skos/core#prefLabel> ?resultObj .}"
					    + "             FILTER(CONTAINS(STR(?subj), ?fil)) "
						+ "				"+RunTTProfLite.triplestore.getLang()
						+ "        } group by ?pred ?resultObj ?subj"
						+ "      }"
						+ "} ";
					
					try {
						new RunQueryAlter(RunTTProfLite.triplestore, queryStr)  {
						}.execute();
					} catch (InterruptedException e) {}
					for(String pred:countAll.keySet()) {
						if(countAll.get(pred)>0) {
							Pattern pattern1 = new Pattern(pattern);
							pattern1.setObjectConcept(terms.get(pred));
							pattern1.setWeight(countAll.get(pred));
							pattern1.setPredicate(pred);
							GetPatterns.setOfPatterns.put(pattern1, GetPatterns.setOfPatterns.get(pattern1));
						}
					}
				}
			//}
			if(GetPatterns.hasNamedIndividual) {
				for(Pattern pattern:set) {
					terms = new HashMap<String, List<String>>();
					countAll = new HashMap<String, Integer>();
					queryStr =""
						+ "SELECT *"
						+ RunTTProfLite.triplestore.getGraph() 
						+ "WHERE {"
						+ "      {"
						+ "         SELECT ?pred ?resultObj ?subj (count(?pred) as ?countPred){ "
						+ "      		?s a <"+pattern.getSubjectConcept()+"> ; ?pred ?subj  . "
						+ "      		?subj <http://www.w3.org/2008/05/skos-xl#altLabel> ?t . "
						+ "				?t <http://www.w3.org/2008/05/skos-xl#literalForm> ?resultObj ."
					    + "             FILTER(regex(STR(?subj), \"frantiq\")) "
						+ "    		} group by ?pred ?resultObj ?subj "
						+ "      }"
						+ "} ";
					
					try {
						new RunQueryAlter2(RunTTProfLite.triplestore, queryStr)  {
						}.execute();
					} catch (InterruptedException e) {}
					//System.out.println(terms+"\n"+countAll.keySet());
					for(String pred:countAll.keySet()) {
						if(countAll.get(pred)>0 && terms.containsKey(pred)) {
							Pattern pattern1 = new Pattern(pattern);
							pattern1.setObjectConcept(terms.get(pred));
							pattern1.setWeight(countAll.get(pred));
							pattern1.setPredicate(pred);
							GetPatterns.setOfPatterns.put(pattern1, GetPatterns.setOfPatterns.get(pattern1));
						}
					}
				}
			}
		//}
	}
}
