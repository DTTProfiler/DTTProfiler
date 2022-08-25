package computeTerms;

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
		if(qs.getResource("predicate")==null)
			return false;

		String predicate  = qs.getResource("predicate").toString();
		GetPatterns.setOfPredicate.put(predicate, null);
		
		return true; 
	}

}
