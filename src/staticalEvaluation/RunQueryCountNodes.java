package staticalEvaluation;

import org.apache.jena.query.QuerySolution;
import tools.SparqlQuerier;
import tools.Triplestore;

public class RunQueryCountNodes extends SparqlQuerier{
	
	public RunQueryCountNodes(Triplestore r, String endpoint) {
		super(r, endpoint);
	}

	@Override
	public void begin() {
		//logger.info("C'est parti pour Onto-Sum !"); // ex�cut� en tout d�but
	}

	@Override
	public void end() {
	}

	@Override
	public boolean fact(QuerySolution qs) throws InterruptedException {		
		if(qs.getLiteral("count")==null)
			return false;
		StaticalEvaluation.cNodes += qs.getLiteral("count").getInt();
		
		return true; 
	}

}
