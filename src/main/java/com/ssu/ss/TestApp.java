package com.ssu.ss;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.json.JSONObject;

public class TestApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("hello");
		
		Settings settings = Settings.builder()
				.put("cluster.name", Constants.CLUSTER_NAME_01).build();
		
		TransportClient client;
		try {
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getLocalHost(), 9300));
			
			JSONObject json = new JSONObject();
	        json.put("name", "lssang");
	        json.put("age", 29);
	        json.put("regtime", System.currentTimeMillis());
	        
			String dataStr = json.toString();
			
			IndexResponse res = client.prepareIndex("myindex", "myDocType")
					.setSource(dataStr, XContentType.JSON)
					.get();
			
			client.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		
        
		
	}

}
