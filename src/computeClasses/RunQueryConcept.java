package computeClasses;

import org.apache.jena.query.QuerySolution;

import patterns.GetPatterns;
import runTTProfLite.RunTTProfLite;
import tools.SparqlQuerier;
import tools.Triplestore;

public class RunQueryConcept extends SparqlQuerier{
	
	public RunQueryConcept(Triplestore r, String endpoint) {
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
		try {
			if(qs.getResource("concept")==null)
				return false;
		}catch(ClassCastException e) {return false;}
		String concept  = qs.getResource("concept").toString();
		if(RunTTProfLite.triplestore.getOntoPref()!=null)
			if(!(concept.contains(RunTTProfLite.triplestore.getOntoPref().replace("\"", ""))))
				return false;

		if(concept.contains("NamedIndividual")) {
			GetPatterns.hasNamedIndividual = true;
			return true;
		}

		if(concept.compareTo("http://www.w3.org/2004/02/skos/core#Concept")==0)
			GetPatterns.hasSkosConcept = true;
					
		if(concept.contains("rdf") || concept.contains("owl") || concept.contains("wikiPageWikiLink") || concept.contains("skos-xl"))
			return false;

		GetClassesAndPredicates.allClasses.add(concept);
		
		return true; 
	}

}
