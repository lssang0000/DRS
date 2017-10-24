package com.ssu.ss.storage;

import java.util.HashMap;
import java.util.Map;

import com.ssu.ss.Constants;

public class QueryExecutor {

	Map<Integer, StorageManager> sm_pool;
	StorageManager curr_sm;
	
	public QueryExecutor() {
		// TODO Auto-generated constructor stub
		
		sm_pool = new HashMap<Integer, StorageManager>();
		sm_pool.put(Constants.REPO_TYPE_ELASTICSEARCH, new ESStorageManager());
		sm_pool.put(Constants.REPO_TYPE_MYSQL, new MSStorageManager());
		
	}

	public int transfortToStorage(int repoType, String[] index, String dataStr) {

		curr_sm = sm_pool.get(repoType);
		
		int status = Constants.DONE;
		if(curr_sm !=null) {
			status = curr_sm.load(repoType, index, dataStr);
		}
		else {
			status = Constants.S_ERROR_DATASTORAGE_NOT_ALLOC;
			System.out.println("[QueryExecutor] StorageManager isn't allocated");
		}
						
		return status;
	}
}
