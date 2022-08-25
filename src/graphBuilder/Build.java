package graphBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstractPatterns.AbstractPattern;
import patterns.GetPatterns;
import patterns.ProfPattern;
import runTTProfLite.RunTTProfLite;

public class Build {
	static HashMap<String, List<String>> idRepresentNodes = new HashMap<String, List<String>>();
	static HashMap<String, String> representNodes = new HashMap<String, String>();
	static HashMap<List<String>, String> allreadySelectedNode = new HashMap<List<String>, String>();
	public static List<DataProperties>  datapropertiesStat = new ArrayList<DataProperties>();
	public static List<String> baniPref = new ArrayList<String>();
	public static boolean baniElem = false;
	
	private GraphProf graph;
	
	@Override
	public String toString() {
		return  graph.toString();
	}

	public Build() {
		String [] tab = {"owl", "rdf", "rdfs", "skos", "wikiPageWikiLink"};
		for(String el:tab)
			baniPref.add(el);
		for(String prefix:GetPatterns.knownPrefix.keySet())
			if(!baniPref.contains(prefix))
				RunTTProfLite.inverseKnownPrefix.put(GetPatterns.knownPrefix.get(prefix), prefix);
 		this.graph = new GraphProf();
		for(ProfPattern patternWithMax:GetPatterns.finalProfil) {
			List<String> maxNodeSubject = patternWithMax.getMaxNodeSubject();
			String uriSubjectNode = getRepresentingNodeFromList(maxNodeSubject);
			String[] elems = nameAndPrefixGraphNodes(uriSubjectNode).split(RunTTProfLite.separator);
			if(!baniElem  && patternWithMax.getMaxNode().getWeight()>0) {
				String idSubject = elems[0];
				RunTTProfLite.PV.add(idSubject);
				String prefixSubject = elems[1];
				RunTTProfLite.distinctTypesAndTerms.addAll(patternWithMax.getMaxNodeSubject());
				RunTTProfLite.distinctTypesAndTerms.addAll(patternWithMax.getMaxNodeObject());
				if(!allreadySelectedNode.containsKey(maxNodeSubject)) {
					allreadySelectedNode.put(maxNodeSubject, uriSubjectNode);
					List<ChildLabel> children_labelsSubject = new ArrayList<ChildLabel>();
					if(RunTTProfLite.getDataproperties)
						getDataproperties(maxNodeSubject);
					for(String child:maxNodeSubject) {
						children_labelsSubject.add(getChildLabelInfo(child));
					}
					if(maxNodeSubject.size()>1)
						graph.addToNodes(new Node(idSubject, prefixSubject, children_labelsSubject, datapropertiesStat, "", ""));
					else
						graph.addToNodes(new Node(idSubject, prefixSubject, datapropertiesStat, children_labelsSubject.get(0).getQuery_String(), children_labelsSubject.get(0).getEndpoint()));
					RunTTProfLite.selectedPrefix.put(prefixSubject, RunTTProfLite.inverseKnownPrefix.get(prefixSubject));
				}
				List<String> maxNodeObject = patternWithMax.getMaxNodeObject();
				String uriObjectNode = getRepresentingNodeFromList(maxNodeObject);
				elems = nameAndPrefixGraphNodes(uriObjectNode).split(RunTTProfLite.separator);
				String idOject= elems[0];
				RunTTProfLite.PV.add(idOject);
				if(uriObjectNode.contains(RunTTProfLite.separator)) {
					String[] confElem = uriObjectNode.split(RunTTProfLite.separator);
					idOject = confElem[0];
					if(confElem[1].contains("_et_al"))
						idOject = idOject + "_et_al";
				}
				String prefixOject = elems[1];
				if(!allreadySelectedNode.containsKey(maxNodeObject)) {
					allreadySelectedNode.put(maxNodeObject, uriObjectNode);
					List<ChildLabel> children_labelsObject = new ArrayList<ChildLabel>();
					if(RunTTProfLite.getDataproperties)
						getDataproperties(maxNodeObject);
					for(String child:maxNodeObject) {
						children_labelsObject.add(getChildLabelInfo(child));
						
					}
					if(maxNodeObject.size()>1)
						graph.addToNodes(new Node(idOject, prefixOject, children_labelsObject, datapropertiesStat, "", ""));
					else
						graph.addToNodes(new Node(idOject, prefixOject, datapropertiesStat, children_labelsObject.get(0).getQuery_String(), children_labelsObject.get(0).getEndpoint()));
					RunTTProfLite.selectedPrefix.put(prefixOject, RunTTProfLite.inverseKnownPrefix.get(prefixOject));
				}
				AbstractPattern pattern = patternWithMax.getMaxNode();
				String source = idSubject;
				String target = idOject;
				String uriPred = pattern.getPredicate();
				elems = nameAndPrefixGraphNodes(uriPred).split(RunTTProfLite.separator);
				String value = elems[0];
				String prefixPred = elems[1];
				Integer stat = pattern.getWeight();
				graph.addToLinks(new Edge(source, target, value, prefixPred, uriPred, stat, pattern.getListOfSubjectConcept(), pattern.getListOfObjectConcept()));
				RunTTProfLite.nbTriples++;
				RunTTProfLite.selectedPrefix.put(prefixPred, RunTTProfLite.inverseKnownPrefix.get(prefixPred));
			}else {
				baniElem = false;
			}
		}
	} 
	
	public ChildLabel getChildLabelInfo(String child) {
		String endpoint = RunTTProfLite.triplestore.getEndpoint();
		String prefix;
		if(child.contains(RunTTProfLite.separator)) {
			String[] el = child.split(RunTTProfLite.separator);
			if(el[1].contains("frantiq"))
				prefix = "frantiq";
			else
				prefix = "thesau";
			String name = el[0];
			String uri = el[1];
			return new ChildLabel(name, prefix, uri, getQueryStringForTerms(el[0]), endpoint);			
		}
		String[] cl = child.split(RunTTProfLite.separator);
		//System.out.println(child);
		String uri;
		if(cl.length>1)
			uri = cl[1];
		else
			uri = cl[0];
		String[] elems = nameAndPrefixGraphNodes(cl[0]).split(RunTTProfLite.separator);
		String name = elems[0];
		prefix = elems[1];
		String query_String;
		if(uri.contains("http") && !cl[0].contains("frantiq") && !cl[0].contains("thesau") )//cl.length>0 && !cl[0].contains("frantiq") && !child.split(":")[0].contains("thesau"))
			 query_String = getQueryStringForConcept(uri);
		 else {
			 query_String = "";
		 }
		child = name;
		return new ChildLabel(name, prefix, uri, query_String, endpoint);
	}
	
	public String getQueryStringForConcept(String concept) {
		String queryStr = "SELECT * "+ RunTTProfLite.triplestore.getGraph()+" WHERE {{ SELECT DISTINCT ?subj ?prop { ?subj ?pred ?obj; a <"+concept+">. ?obj a ?resultObj. filter(!isBlank(?resultObj)) BIND(CONCAT(STR(?pred),'(->)') AS ?prop)}} UNION { SELECT DISTINCT ?subj ?prop { ?subj ?pred ?obj; a ?resultSubj. ?obj a <"+concept+">. filter(!isBlank(?resultSubj)) BIND(CONCAT(STR(?pred),'(<-)') AS ?prop)}}}";
		return queryStr;
	}
	
	public String getQueryStringForTerms(String term) {
		String lang = RunTTProfLite.triplestore.getLangFormat();
		String queryStr="";
		if(!GetPatterns.hasNamedIndividual) {
			String[] info = term.split("@");
			String termRef = info[0];
			queryStr = "SELECT * "+ RunTTProfLite.triplestore.getGraph()+ " WHERE {{ SELECT DISTINCT ?subj ?pred ?obj{ VALUES(?fil) {"+RunTTProfLite.triplestore.getThesau()+"} ?subj ?pred ?obj; a ?resultSubj. FILTER(CONTAINS(STR(?obj), ?fil)). ?obj <http://www.w3.org/2004/02/skos/core#prefLabel> '"+termRef.replace("\n", " ")+"'"+lang+". filter(!isBlank(?resultSubj))}}}";
		}
		else {
			/*
			 * String[] info = term.split(":"); String termRef =
			 * GetPatterns.inverseKnownPrefix.get(info[0])+info[1]; queryStr = "SELECT * "+
			 * RunTTProfLite.triplestore.getGraph()+
			 * " WHERE {{ SELECT DISTINCT ?subj ?pred ?obj{  ?subj ?pred ?obj; a ?resultSubj. FILTER(?resultSubj!=<http://www.w3.org/2002/07/owl#NamedIndividual>) . ?obj a <http://www.w3.org/2002/07/owl#NamedIndividual>. ?obj <http://www.w3.org/2008/05/skos-xl#altLabel> '"
			 * +termRef.replace("\n", " ")+"'"+lang+". filter(!isBlank(?resultSubj))}}}";
			 */}
		return queryStr;
	}
	
	public static String getNameFromURI(String uri) {
		baniElem = false;
		String name;
		String[] prefixSubj= uri.split("/");
		String[] sharpprefixSubj= prefixSubj[prefixSubj.length-1].split("\\#");
		String pref = prefixSubj[0];
		for(int i=1; i<prefixSubj.length-1; i++)
			pref=pref+"/"+prefixSubj[i];
		pref= pref+"/";
		
		if(sharpprefixSubj.length>1) { 
			pref=pref+sharpprefixSubj[0]+"#";
			prefixSubj[prefixSubj.length-1] = sharpprefixSubj[1]; 
		}
		 
		name = sharpprefixSubj[sharpprefixSubj.length-1];
		return name;
	}
	
	static String nameAndPrefixGraphNodes(String uri) {
		baniElem = false;
		String name, prefix;
		String[] prefixSubj= uri.split("/");
		String[] sharpprefixSubj= prefixSubj[prefixSubj.length-1].split("\\#");
		String pref = prefixSubj[0];
		for(int i=1; i<prefixSubj.length-1; i++)
			pref=pref+"/"+prefixSubj[i];
		pref= pref+"/";
		if(sharpprefixSubj.length>1) {
			pref=pref+sharpprefixSubj[0]+"#";
			prefixSubj[prefixSubj.length-1] = sharpprefixSubj[1];
		}
		name = sharpprefixSubj[sharpprefixSubj.length-1];
		String info= "";
		if(pref.contains(RunTTProfLite.separator))
			pref = pref.split(RunTTProfLite.separator)[1];
		
		if(GetPatterns.knownPrefix.containsKey(pref))
			info = GetPatterns.knownPrefix.get(pref);
		else  {
			info = "pref"+(GetPatterns.knownPrefix.size()+1);
		}

		
		if(!baniPref.contains(pref)) {
			//System.out.println("info, pref =  "+info+" *** "+pref);
			GetPatterns.knownPrefix.put(pref, info);
			RunTTProfLite.inverseKnownPrefix.put(info, pref);
		}
		
		prefix = info;		
		
		return name+RunTTProfLite.separator+prefix;
	}
	
	
	
	public String getRepresentingNodeFromList(List<String> maxNode) {
		if(representNodes.containsKey(maxNode.toString()))
			return representNodes.get(maxNode.toString());
		if(maxNode.size()==1) {
			idRepresentNodes.put(maxNode.get(0), maxNode);
			representNodes.put(maxNode.toString(), maxNode.get(0));
			return maxNode.get(0);
		}
		String repNode="";
		for(String rep:maxNode)
			if(!idRepresentNodes.containsKey(rep+"_et_al")) {
				repNode = rep+"_et_al";
				idRepresentNodes.put(repNode, maxNode);
				representNodes.put(maxNode.toString(), repNode);
				return repNode;
			}
		System.out.println("Error!!!");
		System.exit(0);
		return "";
	}
	
	

	
	public void getDataproperties(List<String> allConcepts) {
		datapropertiesStat = new ArrayList<DataProperties>();
		String queryStr ="";
		for(String concept:allConcepts) {
			if(!concept.contains("frantiq") && concept.split(runTTProfLite.RunTTProfLite.separator)[0].contains("http")) {
				queryStr = ""
				+ "SELECT * "
				+ RunTTProfLite.triplestore.getGraph()
				+ "	WHERE {"
				+ "	  	{"
				+ "			SELECT ?domain ?range ?pred (count(?obj) as ?count){"
				+ "    			?subj a <"+concept+">; ?pred ?obj"
				+ "    			filter(isLiteral(?obj)). "
				+ "    			BIND(<"+concept+"> AS ?domain)	"
				+ "    			BIND(DATATYPE(?obj) AS ?range)	"
				+ "			} group by ?domain ?range ?pred"
				+ "		}"
				+ "	}";
				try {
					new RunQuery(RunTTProfLite.triplestore, queryStr)  {
					}.execute();
				} catch (InterruptedException e) {}	
			}
		}
	}
	
}
