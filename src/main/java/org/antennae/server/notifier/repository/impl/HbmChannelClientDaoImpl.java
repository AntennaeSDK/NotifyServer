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

import org.antennae.common.entitybeans.ChannelClient;
import org.antennae.server.notifier.repository.IChannelClientDao;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class HbmChannelClientDaoImpl implements IChannelClientDao {
	
	@Inject
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public void addClient(ChannelClient client) {
		sessionFactory.getCurrentSession().save(client);
	}

	@Override
	@Transactional
	public void updateClient(ChannelClient client) {
		sessionFactory.getCurrentSession().saveOrUpdate(client);
	}

	@Override
	@Transactional
	public void deleteClient(int clientId) {
		ChannelClient client = new ChannelClient();
		client.setId(clientId);
		sessionFactory.getCurrentSession().delete(client);
	}

	@Override
	@Transactional
	public ChannelClient getClient(int clientId) {
		
		ChannelClient result=null;
		
		Query query = sessionFactory.getCurrentSession().createQuery("from ChannelClient where ID = :id");
		query.setParameter("ID", clientId);
		
		List<ChannelClient> clients = (List<ChannelClient>) query.list();
		
		if( clients != null && clients.size() == 1 ){
			result = clients.get(0);
		}
		
		return result;
	}

	@Override
	@Transactional
	public List<ChannelClient> getClientsForChannel(int channelId) {
		
		Query query = sessionFactory.getCurrentSession().createQuery("from ChannelClient where channelId = :channelId");
		query.setParameter("channelId", channelId);
		
		List<ChannelClient> clients = (List<ChannelClient>) query.list();
		return clients;
	}

	@Override
	@Transactional
	public List<ChannelClient> getClientsForUser(int userId) {

		Query query = sessionFactory.getCurrentSession().createQuery("from ChannelClient where userId = :userId");
		query.setParameter("userId", userId);
		
		List<ChannelClient> clients = (List<ChannelClient>) query.list();
		return clients;
	}

	@Override
	@Transactional
	public List<ChannelClient> getAllClients() {
		Query query = sessionFactory.getCurrentSession().createQuery("from ChannelClient");
		
		List<ChannelClient> clients = (List<ChannelClient>) query.list();
		return clients;
	}
}
