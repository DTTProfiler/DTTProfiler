package runTTProfLite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import graphBuilder.Build;
import patterns.GetPatterns;
//import patterns.ProfPattern;
import tools.Triplestore;

public class RunTTProfLite {
	public static Triplestore triplestore =  Triplestore.ARSOL; // Target triplestore to profile
	public static Set<String> nbAbsPatt = new HashSet<String>();
	public static Hashtable<String, String> myArgs = new Hashtable<String, String>(); // user choices
	public static String separator = "___";
	public static int nbNodes = 0;
	public static int nbTriples = 0;
	public static Set<String> PV = new HashSet<String>();
	public static Set<String> distinctTypesAndTerms = new HashSet<String>();
	public static HashMap<String, String> inverseKnownPrefix = new HashMap<String, String>();
	public static HashMap<String, String> selectedPrefix = new HashMap<String, String>();
	public static boolean runNamedIndividual = false;
	public static boolean runSkosprefLabel = true;
	public static boolean runSkosConcept = true;
	public static boolean getDataproperties = true;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println("Profiling "+triplestore.getTriplestoreName()+" ...");
			Date date = new Date();
			System.out.println("Started on "+date.toString());
			long beginTime = System.currentTimeMillis();
			new GetPatterns(); // Get all patterns
			Build g = new Build(); // Build the corresponding abstract pattern
			long endTimeDC = System.currentTimeMillis() - beginTime;
			date = new Date();
			System.out.println("End profiling at  "+date.toString());
			System.out.println("Execution time: "+(endTimeDC/1000F)+" secondes");
			
			
			/*
			 * String out=""; for(ProfPattern e:GetPatterns.finalProfil) { out +=
			 * e.toString()+"\n"; } printData("Output/data.txt", out); // Print the abstract
			 * pattern in a file System.out.println("\n");
			 */
	
	
			String prefixes = "Prefix\n";
			for(String prefix:RunTTProfLite.selectedPrefix.keySet()) {
				prefixes += prefix+":;"+RunTTProfLite.selectedPrefix.get(prefix)+"\n";
			}
			
			printData("Output/"+triplestore.getTriplestoreName()+"_Profile.json", g.toString()); // Print the abstract pattern in a JSON file
			printData("Output/"+triplestore.getTriplestoreName()+"_Prefixes.TXT", prefixes);
			//System.out.println(inverseKnownPrefix+"\n"+prefixes);
			//System.out.println(triplestore.getTriplestoreName());
			//System.out.println("\nExecution time: "+(endTimeDC/1000F)+" secondes");//+g);
			//System.out.println(GetPatterns.inverseKnownPrefix);
			/*System.out.println("|AP|="+nbTriples);
			System.out.println("Nb distinctTypesAndTerms in AP ="+RunTTProfLite.distinctTypesAndTerms.size());
			System.out.println("|PV| ="+RunTTProfLite.PV.size());
			*/
			System.out.println("Distinct Types and Terms : "+RunTTProfLite.distinctTypesAndTerms.size());
			System.out.println("Number of triples : "+nbTriples);
			System.out.println("Size of the profile : "+RunTTProfLite.PV.size());
		}catch(Exception e) {}
	}
	
	// Write the data content in director
	public static void printData(String director, String data) {
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
	
	public static String getPrefix(String uri) {
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
			info = GetPatterns.knownPrefix.get(pref)+":";
		else  {
			info = "pref"+(GetPatterns.knownPrefix.size()+1)+":";
			GetPatterns.knownPrefix.put(pref, info);
		}
		prefix = info.replace(":","");
		return name+RunTTProfLite.separator+prefix;
	}
}
