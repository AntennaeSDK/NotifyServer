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

import javax.inject.Inject;

import org.antennae.common.entitybeans.User;
import org.antennae.server.notifier.service.external.IAuthenticationService;
import org.antennae.server.notifier.transport.XAuthResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLoginController {

	@Inject
	private IAuthenticationService authSvc;
	
	@RequestMapping(value="/api/authentication", method=RequestMethod.POST )
	@ResponseBody
	public XAuthResult signin( @RequestBody String json ){
		
		XAuthResult result = null;
		
		String jsonStr=null;
		User user=null;
		
		try {
			
			jsonStr = URLDecoder.decode(json, "UTF8");
			if( jsonStr.endsWith("}=")){	
				jsonStr = jsonStr.replace("}=", "}");
			}
			
			user = User.fromJson(jsonStr);
			result = authSvc.validateUser( user.getUserId(), user.getPassword() );

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
