package dsp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dsp.engine.DatasetProvider;


@Controller
public class Retrieval {
		
	DatasetProvider dsp;
	
	public Retrieval() {
		// TODO Auto-generated constructor stub
		System.out.println("[Controller.Retrieval] create Controller - Retrieval");
		dsp = DatasetProvider.getInstance();
						
	}
	
	@RequestMapping("/rest")
	@ResponseBody
	public String restTest() {
		System.out.println("[Controller.Retrieval] restTest!");
		
		return "result";
	}
	
	@RequestMapping("/search")
	@ResponseBody
	public String retrieveDataset(@RequestParam(value="dsname", defaultValue="sample") String dsName) {
		System.out.println("[Controller.Retrieval] retrieval! dsname : "+ dsName);
		
		dsp.getDataset(dsName);
		
		return "retrival result";
	}
}
