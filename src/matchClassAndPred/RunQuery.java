package matchClassAndPred;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.QuerySolution;

import patterns.GetPatterns;
import patterns.Pattern;
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
		if(qs.getResource("C")!=null && qs.getResource("P")!=null && qs.getResource("D")!=null) {
			String sub = qs.getResource("C").toString();
			String obj = qs.getResource("D").toString();
			String pred = qs.getResource("P").toString();
			if(obj.contains("rdf") || obj.contains("owl"))
				return false;
			
			List<String> elemObj = new ArrayList<String>();
			elemObj.add(obj);
			GetPatterns.setOfPatterns.put(new Pattern(sub, pred, elemObj, 0), null);
		}
		return true; 
	}

}
