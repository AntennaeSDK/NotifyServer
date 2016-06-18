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

package org.antennae.server.notifier.service.internal.impl;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Base64.Encoder;
import java.util.concurrent.ThreadLocalRandom;

import javax.inject.Inject;

import org.antennae.common.entitybeans.AuthToken;
import org.antennae.server.notifier.repository.IAuthTokenDao;
import org.antennae.server.notifier.service.exception.AuthTokenException;
import org.antennae.server.notifier.service.internal.IAuthTokenService;
import org.springframework.stereotype.Service;

@Service
public class AuthTokenServiceImpl implements IAuthTokenService{

	@Inject
	private IAuthTokenDao authTokenDao;
	
	@Override
	public void addToken(AuthToken token) {
		
		if( token == null ){
			throw new NullPointerException("AuthToken cannot be null");
		}
		if( token.getUserId() == null || 
				token.getUserId().trim().equals("")){
			throw new NullPointerException("UserId cannot be null");
		}
		
		// check whether the token is already present
		// if present, check the token is already persisted
		if( token.getAuthToken() != null &&
				!token.getAuthToken().trim().equals("") ){
			
			AuthToken t = authTokenDao.getToken(token.getAuthToken());
			if( t != null && t.isActive() ){
				// valid token, cannot add again.
				throw new AuthTokenException("Valid AuthToken. Cannot add again");
			}else{
				// generate a new token
				String tokenStr = generateToken(token.getUserId());
				token.setAuthToken(tokenStr);
			}
		}else{
			// generate a new token
			String tokenStr = generateToken(token.getUserId());
			token.setAuthToken(tokenStr);
		}
		
		authTokenDao.addToken(token);
	}

	@Override
	public AuthToken getToken(int id) {
		return authTokenDao.getToken(id);
	}

	@Override
	public AuthToken getToken(String token) {
		return authTokenDao.getToken(token);
	}

	@Override
	public String generateToken(String userId) {
		
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		
		int random = ThreadLocalRandom.current().nextInt();
		
		String keySource = userId + date.toString() + random;
		
		Encoder encoder = Base64.getEncoder();
		byte [] tokenByte = encoder.encode(keySource.getBytes());
		
		String token = new String(tokenByte);
		
		return token;
	}

}
