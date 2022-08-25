package staticalEvaluation;

import org.apache.jena.query.QuerySolution;
import tools.SparqlQuerier2;
import tools.Triplestore;

public class RunQueryCountNodesE4 extends SparqlQuerier2{
	
	public RunQueryCountNodesE4(Triplestore r, String endpoint) {
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
		
		return true; 
	}

}
