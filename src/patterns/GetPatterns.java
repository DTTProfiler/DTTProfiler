package patterns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import abstractPatterns.AbstractPattern;
import abstractPatterns.ComputeAbstractPatterns;
import computeClasses.GetClassesAndPredicates;
import computeStats.GetPatternStats;
import computeTerms.GetTerms;
import matchClassAndPred.Match;


public class GetPatterns {
	public static Map<String, List<String>> setOfConcept = new LinkedHashMap<String, List<String>>();
	public static Map<String, List<String>> setOfPredicate = new LinkedHashMap<String, List<String>>();
	public static Map<String, Integer> freqOfPredicate = new LinkedHashMap<String, Integer>();
	public static Map<Pattern, List<String>> setOfPatterns = new LinkedHashMap<Pattern, List<String>>();
	public static Map<AbstractPattern, List<String>> setOfAbstractPatterns = new LinkedHashMap<AbstractPattern, List<String>>();
	public static HashSet<List<String>> setOfMaxNodes = new HashSet<List<String>>();
	public static HashSet<ProfPattern> finalProfil = new HashSet<ProfPattern>();
	public static HashMap<String, String> knownPrefix = new HashMap<String, String>();
	public static boolean hasNamedIndividual = false;
	public static boolean hasSkosprefLabel = false;
	public static boolean hasSkosConcept = false;
	
	public GetPatterns() {
		String[] tabPrefix = {"http://purl.org/dc/terms/","http://www.w3.org/ns/dcat#","http://www.ics.forth.gr/isl/CRMext/CRMdig.rdfs/","http://erlangen-crm.org/current/","http://www.w3.org/2002/07/owl#","http://jazz.net/ns/rm#", "http://www.w3.org/1999/02/22-rdf-syntax-ns#", "http://arsol.univ-tours.fr/4DACTION/WFICHEWEB/", "http://www.ics.forth.gr/isl/CRMsci/", "http://www.ics.forth.gr/isl/CRMba/", "http://www.ics.forth.gr/isl/CRMarchaeo/", "http://www.cidoc-crm.org/cidoc-crm/", "https://ark.frantiq.fr/ark:/26678/", "http://www.w3.org/2001/XMLSchema#", "http://www.w3.org/2000/01/rdf-schema#", "http://www.w3.org/2004/02/skos/core#", "http://dbpedia.org/resource/", "http://dbpedia.org/ontology/"};
		String[] knownPref = {"purl","dcat","crmdig","ecrm","owl","rm", "rdf", "wficheweb", "crmsci", "crmba", "crmarch", "crm", "frantiq", "xsd", "rdfs", "skos", "dbr", "dbo"};
		for(int i=0; i<knownPref.length; i++) {
			knownPrefix.put(tabPrefix[i], knownPref[i]);
		}
		
		new GetClassesAndPredicates();
		new Match(setOfConcept, setOfPredicate);
		setOfConcept = null;
		setOfPredicate = null;
		new GetPatternStats(new HashSet<Pattern>(setOfPatterns.keySet()));
		new GetTerms(new HashSet<Pattern>(setOfPatterns.keySet()));
		new ComputeAbstractPatterns(new HashSet<Pattern>(setOfPatterns.keySet()));
		setOfPatterns = null;
		new GetProfile();
		setOfAbstractPatterns = null;
	}
	
	// Write the data content in director
	public void printData(String director, String data) {
		File file = new File(director);
        FileOutputStream fr = null;
        OutputStreamWriter br = null;
        try{
            fr =  new FileOutputStream(file);
            br = new OutputStreamWriter(fr, StandardCharsets.UTF_8);
                br.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
}
