package dsp.engine.storage;

import java.net.InetAddress;
import java.util.ArrayList;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;

import dsp.engine.Constants;

public class StorageElasticsearch extends Storage{
	
	TransportClient client;
	Settings settings;
	
	private ArrayList<String> ip;
	private ArrayList<Integer> port;
	
	public StorageElasticsearch() {
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub		
		System.out.println("[StorageElasticsearch] Run ES Thread..");
		this.ip = super.getIp();
		this.port = super.getPort();
		
		if(ip == null || port == null){
			System.out.println("[StorageElasticsearch] No Access Information");
			System.exit(0);
		}
		
		System.out.println("[StorageElasticsearch] Set Cluster ...");
		settings = Settings.builder().put("cluster.name", "ss-cluster-1").build();
		
		System.out.println("[StorageElasticsearch] Set Tranport Client for "+ip.size()+" host(s)...");
		try {			
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip.get(0)), port.get(0)));
			
			for(int i = 1 ; i < ip.size() ; i++){
				client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip.get(i)), port.get(i)));
			}		
		} catch (Exception e) {
			System.out.println("[StorageElasticsearch] error in run()");
			e.printStackTrace();
		}
		
		if(client != null){
			System.out.println("[StorageElasticsearch] connected !!");
			for(String host : ip){
				System.out.println("[StorageElasticsearch] " + host);
			}
		}
		
	}
		
	public JSONObject runQuery(QueryObject qo) {
		// TODO Auto-generated method stub
		
		System.out.println("[StorageElasticsearch.runQuery] type : "+qo.getQeuryType());
		
		JSONObject rJson;
		
		if(!qo.isStatic()) {
			
			ArrayList<String> list = qo.getIndices();
			JSONArray queryResult = new JSONArray();
			
			//need to add code to check indices
			
			SearchResponse res = client.prepareSearch(list.toArray(new String[0]))
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setFrom(0).setSize(2)//qo.getNumofDocs())
					.get();		
			
			SearchHits hits = res.getHits();
			for(SearchHit hit : hits){
				System.out.println("[StorageElasticsearch.runQuery] "+hit.getSource());
			}
			
			return null;
		}
		
		switch(qo.getQeuryType()) {
		case Constants.QUERYTYPE_SET_DATAMAP:	rJson =  setDataMap(); break;
		default:
			rJson = new JSONObject();
			break;
		
		}		
		
		//System.out.println("[StorageElasticsearch.runQuery] "+ rJson.toString());
		return rJson;
	}
	
	private JSONObject setDataMap() {
		
		System.out.println("[StorageElasticsearch] dataMap setting... ");
		JSONObject rJson=new JSONObject();		
			
		ClusterHealthResponse healths = client.admin().cluster().prepareHealth().get();
//		String clusterName = healths.getClusterName();              
//		int numberOfDataNodes = healths.getNumberOfDataNodes();     
//		int numberOfNodes = healths.getNumberOfNodes();    
		
		for (ClusterIndexHealth health : healths.getIndices().values()) { 
		    String index = health.getIndex();                       
//		    int numberOfShards = health.getNumberOfShards();        
//		    int numberOfReplicas = health.getNumberOfReplicas();    
//		    ClusterHealthStatus status = health.getStatus();       
		    
		    try {
			    GetMappingsResponse res = client.admin().indices().getMappings(new GetMappingsRequest().indices(index)).get();
		        ImmutableOpenMap<String, MappingMetaData> mapping  = res.mappings().get(index);
		            
		        for (ObjectObjectCursor<String, MappingMetaData> c : mapping) {
		            //System.out.println(c.key+" = "+c.value.source());
		            //rJson = new JSONObject(c.value.source().toString());
		            rJson.put(index, c.value.source().toString());
		        }
		    }
	        catch (Exception e) {
				// TODO: handle exception
	        	System.out.println("[StorageElasticsearch] error in setDataMap");
	        	e.printStackTrace();
			}
		    	    
		}
		
		//System.out.println("[StorageElasticsearch] "+rJson.toString());
		System.out.println("[StorageElasticsearch] setDataMap done...");	
		return rJson;
	}
}
