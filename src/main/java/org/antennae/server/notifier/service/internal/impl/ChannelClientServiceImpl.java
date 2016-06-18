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

import java.util.List;

import javax.inject.Inject;

import org.antennae.common.entitybeans.ChannelClient;
import org.antennae.server.notifier.repository.IChannelClientDao;
import org.antennae.server.notifier.service.internal.IChannelClientService;
import org.springframework.stereotype.Service;

@Service(value="ChannelClientService")
public class ChannelClientServiceImpl implements IChannelClientService {
	
	@Inject
	private IChannelClientDao channelClientDao;

	@Override
	public void addClient(ChannelClient client) {
		channelClientDao.addClient(client);
	}

	@Override
	public void updateClient(ChannelClient client) {
		channelClientDao.updateClient(client);
	}

	@Override
	public void deleteClient(int clientId) {
		channelClientDao.deleteClient(clientId);
	}

	@Override
	public ChannelClient getClient(int clientId) {
		return channelClientDao.getClient(clientId);
	}

	@Override
	public List<ChannelClient> getClientsForChannel(int channelId) {
		return channelClientDao.getClientsForChannel(channelId);
	}

	@Override
	public List<ChannelClient> getClientsForUser(int userId) {
		return channelClientDao.getClientsForUser(userId);
	}

	@Override
	public List<ChannelClient> getAllClients() {
		return channelClientDao.getAllClients();
	}
}
