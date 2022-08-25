package staticalEvaluation;

import java.util.HashMap;
import java.util.Map;

import tools.Triplestore;

public class StaticalEvaluation {
	public static Map<String, Integer> statFromCtoC = new HashMap<String, Integer>();
	public static Map<String, Integer> statFromCtot = new HashMap<String, Integer>();
	public static Map<String, Integer> statFromCtoNInd = new HashMap<String, Integer>();
	public static Map<String, Integer> statFromCtoSkosConcept= new HashMap<String, Integer>();
	public static Triplestore triplestore =  Triplestore.BENICULTURALI; // Target triplestore to profile
	public static Integer sx=0;
	public static Integer sy=0;
	public static Integer sz=0;
	public static Integer cNodes=0;
	public static Integer cTriples=0;
	public static Integer cNodes1=0;
	public static Integer cNodes2=0;
	public static Integer cNodes3=0;
	public static Integer cNodes4=0;
	
	StaticalEvaluation(){
		
		String queryStr =""
				+ "SELECT * "
				+ triplestore.getGraph()
				+ "	WHERE {"
				+ "	  	{"
				+ "			SELECT ?s (count(?pred) as ?countPred){    "
				+ "      		?s a ?C ; ?pred ?subj  .      "
				+ "      		?subj a ?O . "
				+ "      		FILTER(regex(STR(?C), \"crm\"))  "
				+ "      		FILTER(regex(STR(?O), \"crm\"))  "
				+ "    		} group by ?s "
				+ "		}"
				+ "	}";
		
		/*try {
			new RunQueryStatFromCtoC(triplestore, queryStr)  {
			}.execute();
		} catch (InterruptedException e) {}	
			*/

		queryStr =""
				+ "SELECT * "
				+ triplestore.getGraph()
				+ "	WHERE {"
				+ "	  	{"
				+ "			SELECT (count(distinct ?s) as ?countPred){  "
				//+ "				VALUES(?fil) {(\"crm\")} "
				+ "      		?s a ?C ; ?pred ?subj  .    "
				+ "      		?subj a <http://www.w3.org/2004/02/skos/core#Concept> .   "  
				//+ "     		FILTER(regex(STR(?C), ?fil))   "
				+ "    		} "
				+ "		}"
				+ "	}";
		
		try {
			new RunQueryStatFromCtoSkosConcept(triplestore, queryStr)  {
			}.execute();
		} catch (InterruptedException e) {}
		
		queryStr =""
				+ "SELECT * "
				+ triplestore.getGraph()
				+ "	WHERE {"
				+ "	  	{"
				+ "			SELECT  (COUNT(distinct ?s) AS ?countPred){   "
				+ "             VALUES(?fil) {"+triplestore.getThesau()+"}"
				+ "      		?s a ?C; ?pred ?subj .	"
				+ "      		optional {?subj <http://www.w3.org/2004/02/skos/core#prefLabel> ?resultObj .}"
			    + "             FILTER(CONTAINS(STR(?subj), ?fil)) "
				+ "				"+triplestore.getLang()
				+ "    		} "
				+ "		}"
				+ "	}";
		
		try {
			new RunQueryStatFromCtot(triplestore, queryStr)  {
			}.execute();
		} catch (InterruptedException e) {}	
		System.out.println(sy);

		
		queryStr =""
				+ "SELECT * "
				+ triplestore.getGraph()
				+ "	WHERE {"
				+ "	  	{"
				+ "			SELECT  (count(distinct ?s) as ?countPred){   "
				+ "             VALUES(?fil) {"+triplestore.getThesau()+"}"
				+ "      		?s a ?C ; ?pred ?subj  .    "
				+ "      		?subj <http://www.w3.org/2008/05/skos-xl#altLabel> ?t . "
				+ "				optional {?t <http://www.w3.org/2008/05/skos-xl#literalForm> ?resultObj .}"
			    + "             FILTER(CONTAINS(STR(?subj), ?fil)) "
				//+ "     		FILTER(regex(STR(?C), \"crm\"))   "
				+ "    		} "
				+ "		}"
				+ "	}";
		
		try {
			new RunQueryStatFromCtoNInd(triplestore, queryStr)  {
			}.execute();
		} catch (InterruptedException e) {}

		System.out.println(sz);
		
	}
	

	public static void StatKB() {
		
		String queryStr =""
				+ "SELECT * "
				+ triplestore.getGraph()
				+ "	WHERE {"
				+ "	  	{"
				+ "			select (count(?p) as ?count)  where {?s ?p ?o}"
				+ "		}"
				+ "	}";
		
		/*try {
			new RunQueryCountTriples(triplestore, queryStr)  {
			}.execute();
		} catch (InterruptedException e) {}
		*/
		

		queryStr =""
				+ "SELECT * "
				+ triplestore.getGraph()
				+ "	WHERE {"
				+ "	  	{"
				+ "			select (count(distinct ?s) as ?count)  where {{?s ?p ?o} union {?o ?p ?s}}"
				+ "		}"
				+ "	}";
		
		/*try {
			new RunQueryCountNodes(triplestore, queryStr)  {
			}.execute();
		} catch (InterruptedException e) {}
		*/
		
		queryStr =""
				+ "SELECT * "
				+ triplestore.getGraph()
				+ "	WHERE {"
				+ "	  	{"
				+ "			select (count(distinct ?s) as ?count)  where {?s ?p ?o}"
				+ "		}"
				+ "	}";
		/*
		try {
			new RunQueryCountNodesE1(triplestore, queryStr)  {
			}.execute();
		} catch (InterruptedException e) {}
		*/
		queryStr =""
				+ "SELECT * "
				+ triplestore.getGraph()
				+ "	WHERE {"
				+ "	  	{"
				+ "			select (count(distinct ?s) as ?count)  where {?o ?p ?s}"
				+ "		}"
				+ "	}";
		/*
		try {
			new RunQueryCountNodesE2(triplestore, queryStr)  {
			}.execute();
		} catch (InterruptedException e) {}
		*/
		queryStr =""
				+ "SELECT * "
				+ triplestore.getGraph()
				+ "	WHERE {"
				+ "	  	{"
				+ "			select (count(distinct ?s) as ?count)  where {?s ?p ?o. ?o1 ?p1 ?s}"
				+ "		}"
				+ "	}";
		
		/*try {
			new RunQueryCountNodesE3(triplestore, queryStr)  {
			}.execute();
		} catch (InterruptedException e) {}
		*/
		//**************************************
		
		queryStr =""
				+ "SELECT * "
				+ triplestore.getGraph()
				+ "	WHERE {"
				+ "	  	{"
				+ "			select distinct ?s where {{?s ?p ?o} union {?o ?p ?s}}"
				+ "		}"
				+ "	}";
		
		try {
			new RunQueryCountNodesE4(triplestore, queryStr)  {
			}.execute();
		} catch (InterruptedException e) {}
		
		
	}
	
	public static void main(String[] args) {
		
		new StaticalEvaluation();
		System.out.println(sx+"\t"+sy+"\t"+sz+"\t"+(sx+sy+sz));
		//StatKB();
		//System.out.println("Nb Triples\t"+cTriples+"\tnb Nodes\t"+cNodes);
		//cNodes = cNodes1 + cNodes2 - cNodes3;
		//System.out.println(cNodes1+" "+cNodes2+" "+cNodes3+" "+cNodes4+" & "+cNodes);
	}
}
