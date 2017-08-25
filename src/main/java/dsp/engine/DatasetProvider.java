package dsp.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import dsp.engine.storage.QueryObject;
import dsp.engine.storage.Storage;
import dsp.engine.storage.StorageElasticsearch;

public class DatasetProvider {
	
	//modules
	private static DatasetProvider instance = new DatasetProvider();
	private StorageManager sm;
	private ModelManager mm;
	
	//data list for interface
	private ArrayList<String> m_datasetList;
	private JSONObject m_dataMap;
	
	
	private DatasetProvider() {
		// TODO Auto-generated constructor stub
		System.out.println("[DataProvider] Strat...");
		
		//init Storage : connect with repository
		System.out.println("[DataProvider] Initializing Stroage ...");
		sm = new StorageManager();
		sm.init(Constants.ELASTICSEARCH);				
		m_dataMap = sm.initDataMap();	
		
		//init datasetModel
		System.out.println("[DataProvider] Initializing DatasetModels ...");
		mm = new ModelManager();
		m_datasetList = mm.initDSList();
		
		
		System.out.println("[DataProvider] Init All Done ...");
	}
	
	public static DatasetProvider getInstance(){
		return instance;
	}
	
	public ArrayList<String> getdatasetList(){
		return m_datasetList;
	}
	
	public JSONObject getDataMap(){
		return m_dataMap;
	}
	
	/**
	 * Provide Data-set by using Data-set Name. Call from RESTful("/search")
	 * 1. Model Manager makes a QueryObject from Data-set Model.
	 * 2. Storage Manager executes query by using Query Object.
	 * 3. Preprocessor reforms the result of query.
	 * 
	 * @param dsName
	 * @return CSV File
	 */
	public File getDataset(String dsName){
		mm.setDatasetModel(dsName+".xml"); 
		QueryObject qo = mm.getQueryObject();
		JSONObject json = sm.executeQuery(qo);
		//preprocessing
		
		//need modify
		return null;
	}
	
	/**
	 * Create or Modify data-set Model from User Interface(Web Page)
	 * 
	 * @param dsInfo
	 */			
	public void setDataset(JSONObject dsInfo) {
		
	}
	
	
	
	
}

class StorageManager {
	
	private HashMap<Integer, Storage> pool;
	private ArrayList<String> ip;
	private ArrayList<Integer> port;
	
	public StorageManager() {
		// TODO Auto-generated constructor stub
		pool = new HashMap<Integer, Storage>();
		ip = new ArrayList<String>();
		port = new ArrayList<Integer>();
		
		//need modify
		ip.add("203.253.23.53");		
		port.add(9300);
	}
	
	public void init(int storage) {

		switch (storage) {
		case Constants.ELASTICSEARCH:
			System.out.println("[StroageManager] Try to connect Elastcsearch("+ip.get(0)+":"+port.get(0)+")");
			Storage storageObject = new StorageElasticsearch();
			storageObject.setIp(ip);
			storageObject.setPort(port);
			storageObject.run();		
			pool.put(Constants.ELASTICSEARCH, storageObject);
			break;
			
		case Constants.HADOOP:
			break;
			
		case Constants.MONGODB:
			break;
			
		default:
			break;
		}
	}
	
	public JSONObject initDataMap() {
		
		QueryObject qo = new QueryObject(Constants.QUERYTYPE_SET_DATAMAP);
		JSONObject datamap = pool.get(Constants.ELASTICSEARCH).runQuery(qo);
		
		return datamap;
	}
	
	public JSONObject executeQuery(QueryObject qo){		
		
		//return pool.get(Constants.ELASTICSEARCH).runQuery(qo);
		return pool.get(qo.getStorageId()).runQuery(qo);
	}
}

class ModelManager {
	
	private File dsModel;
	private Document doc;
	private QueryObject queryObejct;
	private File f_CSV;
	private JSONObject f_JSON;
	
	public ModelManager() {
		// TODO Auto-generated constructor stub				
	}
		
	public ArrayList<String> initDSList() {
		
		ArrayList<String> list = new ArrayList<String>();
		
		File dir = new File(Constants.PATH_APSOLUTE_MODELS);
		File[] fileList = dir.listFiles();
		
		try {
			for(int i = 0 ; i< fileList.length ; i ++) {
				File file = fileList[i];
				if(file.isFile()) {
					list.add(file.getName());
				}
			}
			
			
		}catch(Exception e) {
			System.out.println("[Modulemanager] error in initDSList");
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void setDatasetModel(String ds_fName) {
		
		System.out.println("[ModelManager] FileName : " + ds_fName);
		try {
			// need modify
			dsModel = new File(Constants.PATH_APSOLUTE_MODELS + ds_fName);			

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(dsModel);

		} catch (Exception e) {
			System.out.println("[Modulemanager] error in initializer");
			e.printStackTrace();
		}

		doc.getDocumentElement().normalize();

		// set QueryObject
		queryObejct = new QueryObject();
		NodeList list = doc.getElementsByTagName("index");
		for (int i = 0; i < list.getLength(); i++) {
			Element index = (Element) list.item(i);
			queryObejct.addIndex(index.getTextContent());
		}

		Element s = (Element) doc.getElementsByTagName("storage").item(0);
		String sType = s.getAttribute("type");
		if (sType.equals("Elasticsearch")) {
			queryObejct.setStorageId(Constants.ELASTICSEARCH);
		} else if (sType.equals("Hadoop")) {
			queryObejct.setStorageId(Constants.HADOOP);
		} else {

		}

		Element o = (Element) doc.getElementsByTagName("observation").item(0);
		queryObejct.setNumOfDocs(Integer.parseInt(o.getTextContent()));
		// qo.addIndex();
	}

	public QueryObject getQueryObject() {
		return queryObejct;
	}

}