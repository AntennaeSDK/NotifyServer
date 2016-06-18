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

package org.antennae.server.notifier.utils.conversion;

import java.util.ArrayList;
import java.util.List;

import org.antennae.server.notifier.entities.Message;
import org.antennae.server.notifier.transport.ChatWebSocketMessage;

public class XMessageUtils {

	public static ChatWebSocketMessage convertMessageToXWebSocketMessage(Message message ){
		
		if( message == null ){
			throw new NullPointerException("Message cannot be null");
		}
		
		ChatWebSocketMessage wsMsg = new ChatWebSocketMessage();
		
		// common fields
		wsMsg.getChannelId(message.getChannelId());
		wsMsg.setMessageId(message.getId());
		
		if( message.getSenderId() != null ){
			wsMsg.setSenderId(message.getSenderId());
		}
		
			
		// state fields
		if( message.getSentTime() != null ){
			wsMsg.setCreatedTime(message.getSentTime());
		}
		
		// data fields
		if( message.getMessage() != null ){
			wsMsg.setData(message.getMessage());
		}
		if( message.getParent() > 0){
			wsMsg.setParentMessageId(message.getParent());
		}
		wsMsg.setQuestion(message.isQuestion());
		if( message.getAnswerOf() > 0){
			wsMsg.setAnswerOf(message.getAnswerOf());
		}
		
		return wsMsg;
	}
	
	public static List<ChatWebSocketMessage> convertMessageToXWebSocketMessage(List<Message> messages ){
		
		if( messages == null ){
			throw new NullPointerException("Messages cannot be null");
		}
		
		List<ChatWebSocketMessage> msgs = new ArrayList<ChatWebSocketMessage>();
		
		for( Message m : messages ){
			ChatWebSocketMessage msg = convertMessageToXWebSocketMessage(m);
			msgs.add(msg);
		}
		
		return msgs;
	}
	
	
	public static Message convertXWebSocketMessageToMessage( ChatWebSocketMessage wsMsg){
		if( wsMsg == null ){
			throw new NullPointerException("WebSocketMessage cannot be null");
		}
		
		Message msg = new Message();
		
		if( wsMsg.getChannelId() > 0 ){
			msg.setChannelId(wsMsg.getChannelId());
		}		
		if( wsMsg.getMessageId() > 0){
			msg.setId(wsMsg.getMessageId() );
		}
		if( wsMsg.getSenderId() != null ){
			msg.setSenderId(wsMsg.getSenderId());
		}
		if( wsMsg.getParentMessageId() > 0 ){
			msg.setParent(wsMsg.getParentMessageId());
		}
		if( wsMsg.getData() != null ){
			msg.setMessage(wsMsg.getData());
		}
		msg.setQuestion(wsMsg.isQuestion());
		
		if( wsMsg.getAnswerOf() > 0 ){
			msg.setAnswerOf(wsMsg.getAnswerOf());
		}
		
		return msg;
	}
}
