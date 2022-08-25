package computeClasses;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.QuerySolution;

import patterns.GetPatterns;
import tools.SparqlQuerier;
import tools.Triplestore;

public class RunQuery extends SparqlQuerier{
	
	public RunQuery(Triplestore r, String endpoint) {
		super(r, endpoint);
	}

	@Override
	public void begin() {
		//logger.info("C'est parti pour Onto-Sum !"); // exécuté en tout début
	}

	@Override
	public void end() {
	}

	@Override
	public boolean fact(QuerySolution qs) throws InterruptedException {		
		if(qs.getResource("concept")==null || qs.getResource("predicate")==null)
			return false;
		String concept  = qs.getResource("concept").toString();
		String predicate  = qs.getResource("predicate").toString();
		
		if(predicate.contains("altLabel") || predicate.contains("literalForm")) {
			GetPatterns.hasNamedIndividual = true;
			return true;
		}
		if(concept.compareTo("http://www.w3.org/2004/02/skos/core#Concept")==0)
			GetPatterns.hasSkosConcept = true;
					
		if(predicate.contains("rdf") || predicate.contains("skos") || predicate.contains("owl") || predicate.contains("wikiPageWikiLink"))
			return false;

		if(GetPatterns.freqOfPredicate.containsKey(predicate))
			GetPatterns.freqOfPredicate.put(predicate, GetPatterns.freqOfPredicate.get(predicate)+1);
		else
			GetPatterns.freqOfPredicate.put(predicate, 1);
		
		String[] var = predicate.split("_");
		if((Character.isDigit(var[0].charAt(var[0].length()-2)) && var[0].charAt(var[0].length()-1)=='i'))
			return false;
		List<String> listePred = new ArrayList<String>();
		if(GetPatterns.setOfConcept.containsKey(concept))
			listePred = GetPatterns.setOfConcept.get(concept);
		if(!listePred.contains(predicate))
			listePred.add(predicate);
		if(concept.contains("http://www.w3.org/2004/02/skos/core#Concept"))
		System.out.println(concept+"\t"+listePred);
		GetPatterns.setOfConcept.put(concept, listePred);
		
		return true; 
	}

}
