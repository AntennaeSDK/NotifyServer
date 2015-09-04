/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.antennae.server.notifier.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.inject.Inject;

import org.antennae.server.notifier.entities.User;
import org.antennae.server.notifier.service.external.IUserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Inject
	private IUserService userSvc;
	
	@RequestMapping(value="/api/users", method=RequestMethod.POST )
	@ResponseBody
	public User registerUser( @RequestBody String json ){
		
		String jsonStr=null;
		User user=null;
		try {
			
			jsonStr = URLDecoder.decode(json, "UTF8");
			jsonStr = jsonStr.replace("}=", "}");
			user = User.fromJson(jsonStr);
			userSvc.registerUser(user);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	@RequestMapping( value="/api/users", method=RequestMethod.GET)
	@ResponseBody
	public List<User> getUsers(){
		return userSvc.getAllUsers();
	}
	
	@RequestMapping( value="/api/users/{id}", method=RequestMethod.GET)
	@ResponseBody
	public User getUser( @PathVariable("id") Integer id){
		return userSvc.getUser( id.intValue() );
	}
	
	@RequestMapping( value="/api/users/login/{id}", method=RequestMethod.GET)
	@ResponseBody
	public User getUser( @PathVariable("id") String id ){
		return userSvc.getUserByLoginId( id );
	}
}
