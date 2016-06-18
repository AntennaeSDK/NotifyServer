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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.antennae.common.entitybeans.Channel;
import org.antennae.common.beans.ChannelPriorityEnum;
import org.antennae.common.beans.ChannelTypeEnum;
import org.antennae.server.notifier.repository.IChannelDao;
import org.antennae.server.notifier.service.internal.IChannelService;
import org.springframework.stereotype.Service;

@Service
public class ChannelServiceImpl implements IChannelService {
	
	@Inject
	private IChannelDao channelDao;

	@Override
	public void addChannel(Channel channel) {

		if( channel == null ){
			throw new NullPointerException("channel cannot be null");
		}
		if( channel.getName() == null &&
				!channel.getName().trim().equals("") ){
			throw new NullPointerException("channel-name cannot be null or empty");
		}
		if( channel.getSummary() == null &&
				!channel.getSummary().trim().equals("") ){
			throw new NullPointerException("channel-summary cannot be null or empty");
		}
		
		if( channel.getCreatedTime() == null ){
			Date currentTime = Calendar.getInstance().getTime();
			channel.setCreatedTime(currentTime);
		}
		
		channelDao.addChannel(channel);
	}

	@Override
	public Channel getChannel(int channelId) {
		return channelDao.getChannel(channelId);
	}

	@Override
	public void updateChannel(Channel channel) {
		if( channel == null ){
			throw new NullPointerException("channel cannot be null");
		}
		channelDao.updateChannel(channel);
	}

	@Override
	public void deleteChannel(int channelId) {
		channelDao.deleteChannel(channelId);
	}

	@Override
	public List<Channel> getChannels() {
		return channelDao.getChannels();
	}

	@Override
	public List<Channel> getChannels(String createdBy) {
		return channelDao.getChannels(createdBy);
	}

	@Override
	public List<Channel> getChannels(String createdBy, ChannelTypeEnum type) {
		return channelDao.getChannels(createdBy, type);
	}

	@Override
	public List<Channel> getChannels(String createdBy, 
									ChannelTypeEnum type,
									ChannelPriorityEnum priority) {
		return channelDao.getChannels(createdBy, type, priority);
	}

	@Override
	public List<Channel> getChannels(ChannelTypeEnum type) {
		return channelDao.getChannels(type);
	}
}
