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

package org.antennae.server.notifier.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MESSAGE")
public class Message {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="CHANNEL_ID")
	private int channelId;
	
	@Column(name="SENDER_ID")
	private String senderId;
	
	@Column(name="MESSAGE")
	private String message;
	
	@Column(name="SENT_TIME")
	private Date sentTime;
	
	
	// following are attributes of a message
	@Column(name="PARENT")
	private int parent;
	
	@Column(name="QUESTION")
	private boolean isQuestion;
	
	@Column(name="ANSWER_OF")
	private int answerOf;


	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getSentTime() {
		return sentTime;
	}
	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}
	public int getParent() {
		return parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
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
