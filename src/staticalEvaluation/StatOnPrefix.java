package staticalEvaluation;

import tools.Triplestore;




	
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
  

public class StatOnPrefix {

	public static Triplestore triplestore =  Triplestore.DBPEDIA; // Target triplestore to profile
	public static String directory = "Output/"+triplestore.getTriplestoreName()+".JSON";
	
	public static void main(String args[]) {
		System.out.println("-------"+triplestore.getTriplestoreName()+"--------");
		// parsing file "JSONExample.json"
		Object obj = null;
		try {
			obj = new JSONParser().parse(new FileReader(directory));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
		JSONObject jo = (JSONObject) obj;		
		Map<String, Integer> compteur = new HashMap<String, Integer>();		
		JSONArray ja = (JSONArray) jo.get("nodes");
		Iterator itr2 = ja.iterator();		
		System.out.println("Nodes");
		while (itr2.hasNext()) 
        {
            Map itr1 = ((Map) itr2.next());
    		String prefix = (String) itr1.get("prefix");
    		int val = Math.max(1,((JSONArray)itr1.get("children_labels")).size());
    		if(compteur.containsKey(prefix))
    			compteur.put(prefix, compteur.get(prefix)+val);
    		else
    			compteur.put(prefix, val);
        }
		//for(String e:compteur.keySet())
		//System.out.println(e+" :\t"+compteur.get(e));
		
		System.out.println("***************");

		compteur = new HashMap<String, Integer>();		
		ja = (JSONArray) jo.get("links");
		itr2 = ja.iterator();		
		System.out.println("links");
		while (itr2.hasNext()) 
        {
            Map itr1 = ((Map) itr2.next());
    		String prefix = (String) itr1.get("prefix");
    		if(compteur.containsKey(prefix))
    			compteur.put(prefix, compteur.get(prefix)+1);
    		else
    			compteur.put(prefix, 1);
        }
		for(String e:compteur.keySet())
		System.out.println(e+" :\t"+compteur.get(e));
	}
}
