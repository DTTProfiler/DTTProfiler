package staticalEvaluation;

import org.apache.jena.query.QuerySolution;
import tools.SparqlQuerier;
import tools.Triplestore;

public class RunQueryStatFromCtoNInd extends SparqlQuerier{
	
	public RunQueryStatFromCtoNInd(Triplestore r, String endpoint) {
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
		if(qs.getLiteral("countPred")==null)
			return false;
		StaticalEvaluation.sz += qs.getLiteral("countPred").getInt();
		
		return true; 
	}

}
