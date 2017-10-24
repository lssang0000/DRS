package com.ssu.ss.storage;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.ssu.ss.Constants;

public class ESStorageManager implements StorageManager{
	
	TransportClient client;
	
	public ESStorageManager() {
		// TODO Auto-generated constructor stub
		
		System.out.println("[ESStorageManager] init...");
		
		try {			
			Settings settings = Settings.builder()
					.put("cluster.name", Constants.CLUSTER_NAME_01).build();
			
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getLocalHost(), 9300));
			
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}
		
		System.out.println("[ESStorageManager] init... done.");
		
	}
	public void esClientClose() {
		client.close();
	}
	
	@Override
	public int load(int repoType, String[] index, String dataStr) {
		// TODO Auto-generated method stub
		
		System.out.println("[ESStorageManager] data : "+dataStr);
		System.out.println("[ESStorageManager] index1 : "+ index[0]);
		System.out.println("[ESStorageManager] index2 : "+ index[1]);
		
		IndexResponse res = client.prepareIndex(index[0], index[1])
				.setSource(dataStr, XContentType.JSON)
				.get();
		
		System.out.println("[ESSM] load res : "+res);
		
		return Constants.DONE;
	}
}
