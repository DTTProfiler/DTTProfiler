package graphBuilder;

import org.apache.jena.query.QuerySolution;

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
		if(qs.getLiteral("count")==null || qs.getResource("domain")==null)
			return false;
		String domain = Build.getNameFromURI(qs.getResource("domain").toString());
		String range = "";
		if(qs.getResource("range")!=null)
			range = Build.getNameFromURI(qs.getResource("range").toString()); 
		String uri = qs.getResource("pred").toString();
		int count = qs.getLiteral("count").getInt();
		String name= Build.getNameFromURI(uri);
		Build.datapropertiesStat.add(new DataProperties(name, count, domain, range, uri));
		
		return true; 
	}
}
