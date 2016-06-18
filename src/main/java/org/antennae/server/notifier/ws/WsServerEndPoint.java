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

package org.antennae.server.notifier.ws;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.antennae.common.entitybeans.ChannelClient;
import org.antennae.common.beans.ClientStatusEnum;
import org.antennae.common.entitybeans.Message;
import org.antennae.common.entitybeans.User;
import org.antennae.server.notifier.init.SpringApplicationContext;
import org.antennae.server.notifier.service.external.IUserService;
import org.antennae.server.notifier.service.internal.IChannelClientService;
import org.antennae.server.notifier.service.internal.IMessageService;
import org.antennae.server.notifier.service.internal.impl.ChannelClientServiceImpl;
import org.antennae.server.notifier.transport.ChatWebSocketMessage;
import org.antennae.server.notifier.utils.conversion.XMessageUtils;
import org.apache.log4j.Logger;

@ServerEndpoint(value="/ws", configurator=WsServerEndPointConfigurer.class)
public class WsServerEndPoint {

	private static final Logger logger = Logger.getLogger(WsServerEndPoint.class);
	
	private IChannelClientService channelClientSvc;
	private IMessageService messageSvc;
	private IUserService userSvc;
	
	private Set<Session> userSessions = Collections.synchronizedSet(new HashSet<Session>());
	
	public WsServerEndPoint(){
		logger.info("starting the websocket endpoint");
	}
	
	@OnOpen
	public void onOpen( Session userSession ){

		logger.debug("Opened SessionId : " + userSession.getId() );
		userSessions.add(userSession);

		// manually inject the ChannelClientSvc
		// this is done only once
		if( channelClientSvc == null  ){
			Object o = SpringApplicationContext.getBean("ChannelClientService");
			if( o instanceof ChannelClientServiceImpl){
				channelClientSvc = (IChannelClientService) o;
			}
		}
		if( messageSvc == null ){
			Object o = SpringApplicationContext.getBean("MessageServiceImpl");
			if( o instanceof IMessageService ){
				messageSvc = (IMessageService) o;
			}
		}
		if( userSvc == null ){
			Object o = SpringApplicationContext.getBean("UserServiceImpl");
			if( o instanceof IUserService ){
				userSvc = (IUserService) o;
			}
		}
	}
	
	@OnClose
	public void close( Session session ){
		logger.debug("session is closing: " + session.getId());
		userSessions.remove(session);
	}
	
	@OnMessage
	public void onMessage( String message, Session userSession ){
		
		if( message == null ){
			return;
		}
		
		// check whether the message is first message
		ChatWebSocketMessage wsMsg = ChatWebSocketMessage.fromJson(message);
		
		logger.debug("SessionId: " + userSession.getId() + ", message: " + wsMsg.toJson() );
					
		if(channelClientSvc != null ){
			
			if( wsMsg.isFirstMessage() ){
				
				// Register the client for the incident if it is a first message
				
				ChannelClient client = new ChannelClient();
				client.setChannelId(wsMsg.getChannelId());
				client.setToken(wsMsg.getToken());
				client.setCreatedTime(wsMsg.getCreatedTime());
				client.setClientStatus(ClientStatusEnum.LIVE);
				client.setClientType( wsMsg.getClientType() );
				
				channelClientSvc.addClient(client);
				
			}else{
				// persist the message
				Message msg = XMessageUtils.convertXWebSocketMessageToMessage(wsMsg);
				if( msg.getSentTime() == null ){
					Date sentTime = Calendar.getInstance().getTime();
					msg.setSentTime(sentTime);
				}
				messageSvc.addMessage(msg);
				
				// TODO: find clients that subscribe to the incident
				
				// send the message to the subscribers
				ChatWebSocketMessage xwsMsg = XMessageUtils.convertMessageToXWebSocketMessage(msg);
				if( wsMsg.getSenderName() != null ){
					xwsMsg.setSenderName(wsMsg.getSenderName());
				}else{
					User u = userSvc.getUserByLoginId(wsMsg.getSenderId());
					xwsMsg.setSenderName(u.getName());
				}
				
				for( Session s : userSessions ){
					s.getAsyncRemote().sendText(xwsMsg.toJson());
				}
			}
			
		}
	}
}
