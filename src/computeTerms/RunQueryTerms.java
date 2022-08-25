package computeTerms;

import org.apache.jena.query.QuerySolution;

import tools.SparqlQuerier;
import tools.Triplestore;

public class RunQueryTerms extends SparqlQuerier{
	
	public RunQueryTerms(Triplestore r, String endpoint) {
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
		if(qs.getResource("term")==null)
			return false;
		GetTerms.termsFromConcept.add(qs.getResource("term").toString());
		return true; 
	}

}
