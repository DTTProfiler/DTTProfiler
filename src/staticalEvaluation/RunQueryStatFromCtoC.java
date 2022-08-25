package staticalEvaluation;

import org.apache.jena.query.QuerySolution;
import tools.SparqlQuerier;
import tools.Triplestore;

public class RunQueryStatFromCtoC extends SparqlQuerier{
	
	public RunQueryStatFromCtoC(Triplestore r, String endpoint) {
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
		if(qs.getResource("s")==null)
			return false;
		String s  = qs.getResource("s").toString();
		StaticalEvaluation.statFromCtoC.put(s, qs.getLiteral("countPred").getInt());
		
		return true; 
	}

}
