package dsp.engine;

public interface Constants {
	// Stroage
	int NOINITSOTRAGE = 0;
	int ELASTICSEARCH = 1;
	int HADOOP = 2;
	int MONGODB = 3;
	
	String DEFUALT_ES_HOST = "203.253.23.53";
	
	// Directory
	String PATH_APSOLUTE_MODELS = "/home/ss-node1/work-lssang/projects/dsp-0-1-1/src/main/models/";
	
	// Type of pre-prepared Queries
	int QUERYTYPE_DYNAMIC = 0;
	int QUERYTYPE_SET_DATAMAP = 1;
}
