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

import java.util.List;

import javax.inject.Inject;

import org.antennae.server.notifier.entities.Channel;
import org.antennae.server.notifier.entities.ChannelTypeEnum;
import org.antennae.server.notifier.entities.Message;
import org.antennae.server.notifier.service.external.IIncidentService;
import org.antennae.server.notifier.service.internal.IChannelService;
import org.antennae.server.notifier.service.internal.IMessageService;
import org.springframework.stereotype.Service;

@Service
public class IncidentServiceImpl implements IIncidentService {
	
	@Inject
	private IChannelService channelSvc;
	
	@Inject
	private IMessageService messageSvc;

	@Override
	public void addIncident(Channel channel) {
		
		if( channel.getType() != ChannelTypeEnum.INCIDENT.ordinal() ){
			throw new IllegalArgumentException("channel must be of type incident");
		}
		
		channelSvc.addChannel(channel);
	}

	@Override
	public void updateIncident(Channel channel) {
		channelSvc.updateChannel(channel);
	}

	@Override
	public Channel getIncident(int channelId) {
		return channelSvc.getChannel(channelId);
	}

	@Override
	public void deleteIncident(int channelId) {
		channelSvc.deleteChannel(channelId);
	}

	@Override
	public List<Channel> getIncidents() {
		return channelSvc.getChannels(ChannelTypeEnum.INCIDENT);
	}

	@Override
	public List<Channel> getIncidents(String userId) {
		return channelSvc.getChannels( userId, ChannelTypeEnum.INCIDENT);
	}

	@Override
	public List<Message> getMessages(int channelId) {
		return messageSvc.getMessages(channelId);
	}
}
