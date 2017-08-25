package dsp.engine.storage;

import java.util.ArrayList;
import dsp.engine.Constants;


public class QueryObject {
	
	private int storageId;
	private ArrayList<String> indices;
	private int numOfDocs;
	
	private boolean isStatic;
	private int queryType; 
	
	public QueryObject() {
		// TODO Auto-generated constructor stub
		storageId = Constants.NOINITSOTRAGE;
		indices = new ArrayList<String>();
		numOfDocs = 0;
		
		this.queryType = Constants.QUERYTYPE_DYNAMIC;
		isStatic = false;
	}
	
	public QueryObject(int queryType) {
		
		this.queryType = queryType;
		isStatic = true;
	}
	
	public boolean isStatic() {
		return isStatic;
	}
	
	public int getQeuryType() {
		return queryType;
	}
	
	public void setStorageId(int storage) {
		this.storageId = storage;
	}
	
	public void addIndex(String es_index){
		indices.add(es_index);
	}
	
	public ArrayList<String> getIndices(){
		return indices;
	}
			
	public void setNumOfDocs(int num){
		numOfDocs = num;
	}
	
	public int getStorageId() {
		return storageId;
	}
	
	public int getNumofDocs(){
		return numOfDocs;
	}
}
