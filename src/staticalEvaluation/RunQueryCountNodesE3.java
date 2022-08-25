package staticalEvaluation;

import org.apache.jena.query.QuerySolution;
import tools.SparqlQuerier;
import tools.Triplestore;

public class RunQueryCountNodesE3 extends SparqlQuerier{
	
	public RunQueryCountNodesE3(Triplestore r, String endpoint) {
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
		if(qs.getLiteral("count")==null)
			return false;
		StaticalEvaluation.cNodes3 += qs.getLiteral("count").getInt();
		
		return true; 
	}

}
