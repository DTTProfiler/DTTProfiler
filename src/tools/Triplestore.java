package tools;

public enum Triplestore {
	BRITISHART("http://collection.britishart.yale.edu/sparql/", null, "en", null, "BRITISHART", null),
	KERAMEIKOS("http://kerameikos.org/sparql", null, "en", null, "KERAMEIKOS", null),
	WARSAMPO("http://ldf.fi/warsa/sparq", null, "en", null, "WARSAMPO", null),
	SMITHSONIAN("http://edan.si.edu/saam/sparql", null, "en", "('vocabulary')", "SMITHSONIAN", null),
	MappingManuscripts("http://ldf.fi/mmm/sparql", null, "en", null, "MappingManuscripts", null),
	KARAMEIKOS("http://kerameikos.org/sparql", null, "en", null, "KARAMEIKOS", null),
	FOKO("https://foko-project.eu/api/sparql", null, "en", null, "FOKO", null),
	EUROPEANA("http://sparql.europeana.eu/", null, "en", null, "EUROPEANA", null),
	DOREMUS("http://data.doremus.org/sparql", null, "en", "('vocabulary')", "DOREMUS", null),
	SILKNOW("https://data.silknow.org/sparql", null, "en", "('vocabulary')", "SILKNOW", null),
	AUCKLAND("https://api.aucklandmuseum.com/sparql", null, null, null, "AUCKLAND", null),
	ARIADENEPLUS("http://146.48.122.52/repositories/ariadneplus-pr01","http://146.48.122.52/repositories/ariadneplus-pr01", "en", "('aat')", "ARIADENEPLUS", null),
	AAC("http://data.americanartcollaborative.org/sparql", null, "en", null, "AAC", null),
	UNIFORMA2("https://agrovoc.uniroma2.it/sparql/", null, "en", null, "UNIFORMA2", null),
	UNESCO("http://vocabularies.unesco.org/sparql-form", null, "en", null, "UNESCO", null),
	CULTURE("http://data.culture.fr/thesaurus/sparql", null, "en", null, "CULTURE", null),
	DBPEDIA("https://dbpedia.org/sparql",null, "en", null, "DBPEDIA", "dbpedia.org/ontology/"),
	YAGO("https://yago-knowledge.org/sparql/query", null, "en", null, "YAGO", "yago-knowledge.org/"),
	BNF("https://data.bnf.fr/sparql", null, "en", null, "BNF", null),
	CULTURAITALIA("http://dati.culturaitalia.it/sparql",null, "en", "('frantiq')", "CULTURAITALIA", null),
	KITION("http://openarchaeo.huma-num.fr/federation/sparql","http://openarchaeo.huma-num.fr/federation/sources/kition-pervolia", "fr", "('frantiq')", "KITION", null),
	ARSOL("http://openarchaeo.huma-num.fr/federation/sparql","http://openarchaeo.huma-num.fr/federation/sources/arsol", null, "('frantiq')", "ARSOL", null),
	EPICHERCHELL("http://openarchaeo.huma-num.fr/federation/sparql","http://openarchaeo.huma-num.fr/federation/sources/epicherchell", null, "('frantiq')", "EPICHERCHELL", null),
	OUTAGR("http://openarchaeo.huma-num.fr/federation/sparql","http://openarchaeo.huma-num.fr/federation/sources/outagr", "fr", "('frantiq')", "OUTAGR", null),
	RITA("http://openarchaeo.huma-num.fr/federation/sparql","http://openarchaeo.huma-num.fr/federation/sources/rita", null, "('frantiq')", "RITA", null),
	SRAIDF("http://openarchaeo.huma-num.fr/federation/sparql","http://openarchaeo.huma-num.fr/federation/sources/sraidf", "fr", "('frantiq')", "SRAIDF", null),
	AERBA("http://openarchaeo.huma-num.fr/federation/sparql","http://openarchaeo.huma-num.fr/federation/sources/aerba", "fr", "('frantiq')", "AERBA", null),
	CHRONIQUE("http://openarchaeo.huma-num.fr/federation/sparql","http://openarchaeo.huma-num.fr/federation/sources/chronique", "fr", "('frantiq')", "CHRONIQUE", null),
	ICERAMM("http://openarchaeo.huma-num.fr/federation/sparql","http://openarchaeo.huma-num.fr/federation/sources/iceramm", null, "('frantiq')", "ICERAMM", null),
	ADS("http://data.archaeologydataservice.ac.uk/query/", "http://data.archaeologydataservice.ac.uk", "en", null, "ADS", null),
	BENICULTURALI("https://dati.beniculturali.it/sparql", null, "en", "('frantiq')", "BENICULTURALI", null),
	SOLIDAR("http://openarchaeo.huma-num.fr/federation/sparql", "http://openarchaeo.huma-num.fr/federation/sources/solidar", "fr", "('frantiq')", "SOLIDAR", null),
	NOMISMA("http://nomisma.org/sparql", null, "en", null, "NOMISMA", null);
	
	private String endpoint;
	private String graph;
	private String lang;
	private String thesau;
	private String triplestoreName;
	private String ontoPref;


	
	private Triplestore(String endpoint, String graph, String lang, String thesau, String triplestoreName, String ontoPref) {
		this.endpoint = endpoint;
		this.graph = graph;
		this.lang = lang;
		this.thesau = thesau;
		this.triplestoreName = triplestoreName;
		this.ontoPref = ontoPref;
	}
	
	public String getEndpoint() {
		return endpoint;
	}
	
	public String getOntoPref() {
		if(ontoPref!=null)
			return "\""+ontoPref+"\"";
		return null;
	}
	
	public String getGraph() {
		if (graph == null)
			return "";
		else
			return "FROM NAMED <" + graph + "> ";
	}
	
	public String getLang() {
		if (lang == null)
			return "";
		else
			return "FILTER (lang(?resultObj)='"+lang+"')";
	}
	
	public String getLangFormat() {
		if (lang == null)
			return "";
		else
			return "@"+lang;
	}
	
	public String getThesau() {
		if (thesau == null)
			return "";
		else
			return thesau;
	}
	
	public String getTriplestoreName() {
		return this.triplestoreName;
	}
	
}
