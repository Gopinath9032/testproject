package com.gofindo.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gofindo.services.TestService;

@RestController
@ComponentScan("com.gofindo.services")
public class TestController {
	static Logger logger = Logger.getLogger(TestController.class);
	@Autowired
	private TestService ts;
	
	@RequestMapping("/")
	public String index(){
		logger.info("first app is started. No worries.");
		String result = ts.itIsTestService();
		System.out.println(result);
		return "Hey! I am running well! -"+new Date();
	}

}
