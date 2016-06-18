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

import org.antennae.common.entitybeans.Message;
import org.antennae.server.notifier.repository.IMessageDao;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class HbmMessageDaoImpl implements IMessageDao {

	@Inject
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public void addMessage(Message message) {
		sessionFactory.getCurrentSession().save(message);
	}

	@Override
	@Transactional
	public Message getMessage(int messageId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Message where id = :ID ");
		query.setInteger("ID", messageId);
		
		Message result =  null;
		
		List<Message> msgs = (List<Message>) query.list();
		if( msgs != null && msgs.size() == 1 ){
			result = msgs.get(0);
		}
		return result;
	}

	@Override
	@Transactional
	public void updateMessage(Message message) {
		sessionFactory.getCurrentSession().saveOrUpdate(message);
	}

	@Override
	@Transactional
	public void deleteMessage(int messageId) {
		Message m = new Message();
		m.setId(messageId);
		sessionFactory.getCurrentSession().delete(m);
	}

	@Override
	@Transactional
	public List<Message> getMessages(int channelId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Message where channelId = :channelId");
		query.setInteger("channelId", channelId);
		
		List<Message> messages = (List<Message>) query.list();
		return messages;
	}

	@Override
	@Transactional
	public List<Message> getMessages() {
		Query query = sessionFactory.getCurrentSession().createQuery("from Message");
		List<Message> messages = (List<Message>)  query.list();
		return messages;
	}
}
