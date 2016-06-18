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

import org.antennae.common.entitybeans.Message;
import org.antennae.server.notifier.repository.IMessageDao;
import org.antennae.server.notifier.service.internal.IMessageService;
import org.springframework.stereotype.Service;

@Service(value="MessageServiceImpl")
public class MessageServiceImpl implements IMessageService {

	@Inject
	private IMessageDao messageDao;
	
	@Override
	public void addMessage(Message message) {
		messageDao.addMessage(message);
	}

	@Override
	public Message getMessage(int messageId) {
		return messageDao.getMessage(messageId);
	}

	@Override
	public void updateMessage(Message message) {
		messageDao.updateMessage(message);
	}

	@Override
	public void deleteMessage(int messageId) {
		messageDao.deleteMessage(messageId);
	}

	@Override
	public List<Message> getMessages(int channelId) {
		return messageDao.getMessages(channelId);
	}

	@Override
	public List<Message> getMessages() {
		return messageDao.getMessages();
	}
}
