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

package org.antennae.server.notifier.service.external.impl;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import org.antennae.common.entitybeans.AuthToken;
import org.antennae.server.notifier.service.external.IAuthenticationService;
import org.antennae.server.notifier.service.internal.IAuthTokenService;
import org.antennae.server.notifier.service.internal.IUserInternalService;
import org.antennae.server.notifier.transport.XAuthResult;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

	@Inject
	private IAuthTokenService authTokenSvc;
	
	@Inject
	private IUserInternalService userInternalSvc;
	
	@Override
	public XAuthResult validateUser(String userId, String password) {
		
		
		XAuthResult result = new XAuthResult();
		
		result.setUserId(userId);
		
		boolean isValidUser =  userInternalSvc.validateUserPassword(userId, password);
				
		if( isValidUser ){
			
			Date currentTime = Calendar.getInstance().getTime();
			
			AuthToken token = new AuthToken();
			token.setUserId(userId);
			token.setCreationDate(currentTime);
			
			// token will be generated and stored in DB.
			authTokenSvc.addToken(token);
			
			result.setToken(token.getAuthToken());
			result.setExpiryDate(token.getExpiryDate());
			result.setMessage("Login Successful");
			result.setSuccess(true);
		}else{
			result.setSuccess(false);
			result.setMessage("invalid user or password");
		}

		return result;
	}

}
