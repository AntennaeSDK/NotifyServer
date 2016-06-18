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

package org.antennae.server.notifier.transport;

import java.util.Date;

import com.google.gson.Gson;
import org.antennae.common.beans.ClientTypeEnum;
import org.springframework.web.socket.TextMessage;

public class ChatWebSocketMessage {
	
	// common fields
	private int messageId;
	private int channelId;
	private String token;
	private String senderId; // userId (String) of the sender
	private String senderName;
	
	// fields related to state ( may not be present on all messages )
	private boolean firstMessage;
	private ClientTypeEnum clientType;
	private Date createdTime;
	
	// fields related to data 
	private String data;
	private int parentMessageId;
	private boolean isQuestion;
	private int answerOf;

	// utility methods
	public String toJson(){
		Gson gson = new Gson();
		return gson.toJson( this);
	}
	public static ChatWebSocketMessage fromJson(String json ){
		Gson gson = new Gson();
		return gson.fromJson(json, ChatWebSocketMessage.class);
	}

	public TextMessage toSpringTextWebSocketMessage(){
		String json = toJson();
		TextMessage textMessage = new TextMessage( json);
		return textMessage;
	}

	// generated getters and setters
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}	
	public int getChannelId() {
		return channelId;
	}
	public void getChannelId(int incident) {
		this.channelId = incident;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String userId) {
		this.senderId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public boolean isFirstMessage() {
		return firstMessage;
	}
	public void setFirstMessage(boolean isFirstMessage) {
		this.firstMessage = isFirstMessage;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public ClientTypeEnum getClientType() {
		return clientType;
	}
	public void setClientType(ClientTypeEnum clientType) {
		this.clientType = clientType;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public int getParentMessageId() {
		return parentMessageId;
	}
	public void setParentMessageId(int parentMessageId) {
		this.parentMessageId = parentMessageId;
	}
	public boolean isQuestion() {
		return isQuestion;
	}
	public void setQuestion(boolean isQuestion) {
		this.isQuestion = isQuestion;
	}
	public int getAnswerOf() {
		return answerOf;
	}
	public void setAnswerOf(int answerOf) {
		this.answerOf = answerOf;
	}
	
	

}
