package com.ssu.ss;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssu.ss.storage.QueryExecutor;

@RestController
public class StorageControlelr {
	
	final static QueryExecutor qe = new QueryExecutor();
	
	@RequestMapping(value = "/load/post", method = RequestMethod.POST)
	public String load(String repoType, String index1, String index2, String dataStr) {
		
		System.out.println("call");
		
		int repoType_numeric = Constants.REPO_TYPE_NONE;
		String[] index = new String[2];
		index[0] = index1;
		index[1] = index2;
		
		if(repoType.equals("elasticsearch"))
			repoType_numeric = Constants.REPO_TYPE_ELASTICSEARCH;
			
		if(repoType.equals("mysql"))
			repoType_numeric = Constants.REPO_TYPE_MYSQL;
		
		qe.transfortToStorage(repoType_numeric, index, dataStr);
		
		return "done";
	}
}
