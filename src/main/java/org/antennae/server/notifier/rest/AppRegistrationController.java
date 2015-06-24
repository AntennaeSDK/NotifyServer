package org.antennae.server.notifier.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppRegistrationController {

	@RequestMapping(value="/app/registration", method=RequestMethod.POST)
	@ResponseBody
	public void register( @RequestBody String json){
		System.out.println("JSON : " + json );
	}
	
	@RequestMapping(value="/app/registration", method=RequestMethod.GET)
	@ResponseBody
	public void get(){
		System.out.println("Received the GET Api call");
	}
}
