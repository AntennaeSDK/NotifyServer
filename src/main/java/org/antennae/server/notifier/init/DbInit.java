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

package org.antennae.server.notifier.init;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.antennae.common.entitybeans.Channel;
import org.antennae.common.beans.ChannelPriorityEnum;
import org.antennae.common.beans.ChannelStatusEnum;
import org.antennae.common.beans.ChannelTypeEnum;
import org.antennae.common.entitybeans.Message;
import org.antennae.common.entitybeans.User;
import org.antennae.common.beans.UserStatusEnum;
import org.antennae.server.notifier.service.exception.UserIdAlreadyRegisteredException;
import org.antennae.server.notifier.service.external.IUserService;
import org.antennae.server.notifier.service.internal.IChannelService;
import org.antennae.server.notifier.service.internal.IMessageService;
import org.springframework.stereotype.Component;

@Component
public class DbInit {

	@Inject
	private IUserService userService;
	
	@Inject
	private IChannelService channelService;
	
	@Inject
	private IMessageService messageService;
	
	@PostConstruct
	public void init(){
		
		
		List<User> users = new ArrayList<User>();
		// first create users
		
		User user1 = new User();
		user1.setUserId("test1");
		user1.setName("Test User1");
		user1.setPassword("test123");
		user1.setPhone("408-677-7764");
		user1.setStatus(UserStatusEnum.ACTIVE);
		
		try{
			userService.registerUser(user1);
		}catch( UserIdAlreadyRegisteredException e){
			// ignore
		}
		
		User user2 = new User();
		user2.setUserId("test2");
		user2.setName("Test User2");
		user2.setPassword("test123");
		user2.setPhone("408-677-7787");
		user2.setStatus(UserStatusEnum.ACTIVE);
		
		try{
			userService.registerUser(user2);
		}catch( UserIdAlreadyRegisteredException e){
			// ignore
		}
		
		User user3 = new User();
		user3.setUserId("test3");
		user3.setName("Test User3");
		user3.setPassword("test123");
		user3.setPhone("408-677-7387");
		user3.setStatus(UserStatusEnum.ACTIVE);
		
		try{
			userService.registerUser(user3);
		}catch( UserIdAlreadyRegisteredException e){
			// ignore
		}
		
		users.add(user1);
		users.add(user2);
		users.add(user3);
		
		
		// create sample channels
		 Channel channel1 = new Channel();		 
		 channel1.setCreatedBy( user1.getUserId());
		 channel1.setCreatedTime(Calendar.getInstance().getTime());
		 channel1.setName("DDoS Attack on the site");
		 channel1.setSummary("The Attack Started at 9am, manifested in the european cluster first");
		 channel1.setPriority(ChannelPriorityEnum.SEVERE.ordinal());
		 channel1.setType(ChannelTypeEnum.INCIDENT.ordinal());
		 channel1.setStatus(ChannelStatusEnum.OPEN.ordinal());
		 
		 channelService.addChannel(channel1);
		 
		 Channel channel2 = new Channel();		 
		 channel2.setCreatedBy( user3.getUserId());
		 channel2.setCreatedTime(Calendar.getInstance().getTime());
		 channel2.setName("Mongo DB cluster down");
		 channel2.setSummary("Mongo DB cluster experienced high load at 5pm, went down in 30 minutes");
		 channel2.setPriority(ChannelPriorityEnum.ELEVATED.ordinal());
		 channel2.setType(ChannelTypeEnum.INCIDENT.ordinal());
		 channel2.setStatus(ChannelStatusEnum.OPEN.ordinal());
		 
		 channelService.addChannel(channel2);
		 
		 
		 Channel channel3 = new Channel();		 
		 channel3.setCreatedBy( user3.getUserId() );
		 channel3.setCreatedTime(Calendar.getInstance().getTime());
		 channel3.setName("Power Outage in Munich datacenter");
		 channel3.setSummary("Munich had historic snow, it disrupted the power lines, the datacenter ran out of diesel in 15 hours");
		 channel3.setPriority(ChannelPriorityEnum.ELEVATED.ordinal());
		 channel3.setType(ChannelTypeEnum.INCIDENT.ordinal());
		 channel3.setStatus(ChannelStatusEnum.OPEN.ordinal());
		 
		 channelService.addChannel(channel3);

		 // add messages for these channels
		 List<Channel> channels = new ArrayList<Channel>();
		 channels.add(channel1);
		 channels.add(channel2);
		 channels.add(channel3);
		 
		 List<Message> messages = createMessagesForChannel(channels, users);
		 addMessages(messages);
	}

	public List<Message> createMessagesForChannel( List<Channel> channels, List<User> users){
		List<Message> messages = new ArrayList<Message>();
		
		int number = 10;
		for( Channel channel : channels ){
			for( User u : users){
				for( int i=0; i<3; i++){
					
					Message m = new Message();
					m.setMessage("test message " + number);
					m.setChannelId(channel.getId());
					m.setSenderId(u.getUserId());
					
					messages.add(m);
					
					number= number +2;				
				}
			}			
		}
		
		return messages;
	}
	
	public void addMessages( List<Message> messages ){
		for( Message m : messages ){
			messageService.addMessage(m);
		}
	}
	
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IChannelService getChannelService() {
		return channelService;
	}

	public void setChannelService(IChannelService channelService) {
		this.channelService = channelService;
	}
}
