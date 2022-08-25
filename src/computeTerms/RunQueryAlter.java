package computeTerms;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.QuerySolution;

import runTTProfLite.RunTTProfLite;
import tools.SparqlQuerier;
import tools.Triplestore;

public class RunQueryAlter extends SparqlQuerier{
	
	public RunQueryAlter(Triplestore r, String endpoint) {
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
		if(qs.getLiteral("countPred")==null || qs.getLiteral("resultObj")==null || qs.getResource("pred")==null || qs.getResource("subj")==null)
			return false;
		String uri = qs.getResource("subj").toString();
		String pred = qs.getResource("pred").toString();
		String resObj = qs.getLiteral("resultObj").toString();
		if(resObj.length()==0)
			resObj = uri;
		if(resObj.length()==0)
			return false;
		String[] var = pred.split("_");
		if((Character.isDigit(var[0].charAt(var[0].length()-2)) && var[0].charAt(var[0].length()-1)=='i'))
			return false;
		
		if(uri.contains("rdf") || uri.contains("skos") || uri.contains("owl") || uri.contains("wikiPageWikiLink") ||
				pred.contains("rdf") || pred.contains("skos") || pred.contains("owl") || pred.contains("wikiPageWikiLink"))
			return false;
		
		int freq = qs.getLiteral("countPred").getInt();
		if(GetTerms.countAll.containsKey(pred))
			GetTerms.countAll.put(pred, GetTerms.countAll.get(pred) + freq);
		else
			GetTerms.countAll.put(pred, freq);
		List<String> listOfElem = new ArrayList<String> ();
		if(GetTerms.terms.containsKey(pred))
			listOfElem = GetTerms.terms.get(pred);
		listOfElem.add(resObj+RunTTProfLite.separator+uri.replace("\"", ""));
		GetTerms.terms.put(pred, listOfElem); 
		return true; 
	}

}
