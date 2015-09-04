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

import org.antennae.server.notifier.entities.Channel;
import org.antennae.server.notifier.entities.ChannelPriorityEnum;
import org.antennae.server.notifier.entities.ChannelTypeEnum;
import org.antennae.server.notifier.repository.IChannelDao;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class HbmChannelDaoImpl implements IChannelDao {
	
	@Inject
	private SessionFactory sessionFactory;


	@Override
	@Transactional
	public void addChannel(Channel channel) {
		sessionFactory.getCurrentSession().save(channel);
	}

	@Override
	@Transactional
	public Channel getChannel(int channelId) {
		
		Channel result = null;
		
		Query query = sessionFactory.getCurrentSession().createQuery("from Channel where id=:id");
		query.setInteger("id", channelId);
		
		List<Channel> channels = query.list();
		if( channels != null 
				&& channels.size() >0 ){
			result = channels.get(0);
		}
		
		return result;
	}

	@Override
	@Transactional
	public void updateChannel(Channel channel) {
		sessionFactory.getCurrentSession().saveOrUpdate(channel);
	}

	@Override
	@Transactional
	public void deleteChannel(int channelId) {
		
		Channel channel = new Channel();
		channel.setId(channelId);
		
		sessionFactory.getCurrentSession().delete( channel );
	}

	@Override
	@Transactional
	public List<Channel> getChannels() {
		Query query = sessionFactory.getCurrentSession().createQuery("from Channel");
		List<Channel> channels = query.list();
		return channels;
	}

	@Override
	@Transactional
	public List<Channel> getChannels(String createdBy) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Channel where createdBy=:by");
		query.setString("by", createdBy);
		
		List<Channel> channels =  (List<Channel>) query.list();
		return channels;
	}

	@Override
	@Transactional
	public List<Channel> getChannels(String createdBy, ChannelTypeEnum type) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Channel where createdBy=:by and type= :type");
		query.setString("by", createdBy);
		query.setInteger("type", type.ordinal());
		
		List<Channel> channels = query.list();
		return channels;
	}

	@Override
	@Transactional
	public List<Channel> getChannels(String createdBy, ChannelTypeEnum type, ChannelPriorityEnum priority) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Channel where createdBy=:by and type= :type and priority= :priority");
		query.setString("by", createdBy);
		query.setInteger("type", type.ordinal());
		query.setInteger("priority", priority.ordinal());
		
		List<Channel> channels = query.list();
		return channels;
	}

	@Override
	@Transactional
	public List<Channel> getChannels(ChannelTypeEnum type) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Channel where type= :type ");
		query.setInteger("type", type.ordinal());
		
		List<Channel> channels = query.list();
		return channels;
	}
}
