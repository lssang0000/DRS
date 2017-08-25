package dsp.engine.storage;

import java.util.ArrayList;
import org.json.JSONObject;

public abstract class Storage implements Runnable{
	
	private ArrayList<String> ip;
	private ArrayList<Integer> port;
	
	public abstract JSONObject runQuery(QueryObject qo);
		
	public void setIp(ArrayList<String> ip) {
		this.ip = ip;
	}
	
	public void setPort(ArrayList<Integer> port) {
		this.port = port;
	}
	
	protected ArrayList<String> getIp() {
		return ip;
	}
	
	protected ArrayList<Integer> getPort() {
		return port;
	}
	
	public void run(){
		
	}	
}