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

package org.antennae.server.notifier.repository.impl;

import java.util.List;

import javax.inject.Inject;

import org.antennae.common.entitybeans.AuthToken;
import org.antennae.server.notifier.repository.IAuthTokenDao;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class HbmAuthTokenDaoImpl implements IAuthTokenDao {
	
	@Inject
	private SessionFactory sessionFactory;
	

	@Override
	@Transactional
	public void addToken(AuthToken token) {
		sessionFactory.getCurrentSession().save(token);

	}

	@Override
	public AuthToken getToken(int tokeId) {
		
		AuthToken result = null;
		Query query = sessionFactory.getCurrentSession().createQuery("from AuthToken where id= :id");
		query.setInteger("id", tokeId);
		
		List<AuthToken> tokens = (List<AuthToken>) query.list();
		
		if( tokens != null && tokens.size()>0 ){
			result = tokens.get(0);
		}
		
		return result;
	}

	@Override
	public void updateToken(AuthToken token) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteToken(int tokenId) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public AuthToken getToken(String token) {
		AuthToken result = null;
		Query query = sessionFactory.getCurrentSession().createQuery("from AuthToken where authToken= :token");
		query.setString("token", token);
		
		List<AuthToken> tokens = (List<AuthToken>) query.list();
		
		if( tokens != null && tokens.size()>0 ){
			result = tokens.get(0);
		}
		
		return result;
	}

	@Override
	public List<AuthToken> getTokensForDevice(String deviceId) {
		
		Query query = sessionFactory.getCurrentSession().createQuery("from AuthToken where deviceId= :device");
		query.setString("device", deviceId);
		
		List<AuthToken> tokens = (List<AuthToken>) query.list();
				
		return tokens;
	}

	@Override
	public List<AuthToken> getTokensForUser(String userId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from AuthToken where userId= :user");
		query.setString("user", userId);
		
		List<AuthToken> tokens = (List<AuthToken>) query.list();
				
		return tokens;
	}

	@Override
	public List<AuthToken> getTokensForApp(String appId, String appVersion) {
		
		Query query = sessionFactory.getCurrentSession().createQuery("from AuthToken where appId= :appId and appVersion= :version");
		query.setString("appId", appId);
		query.setString("appVersion", appVersion );
		
		List<AuthToken> tokens = (List<AuthToken>) query.list();
				
		return tokens;
	}

}
