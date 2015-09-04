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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.Gson;

@Entity
@Table(name="CHANNEL")
public class Channel {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="NAME")
	private String name;
	
	@Column(name="SUMMARY")
	private String summary;
	
	@Column(name="STATUS")
	private int status = ChannelStatusEnum.OPEN.ordinal();
	
	@Column(name="TYPE")
	private int type = ChannelTypeEnum.INCIDENT.ordinal();
	
	@Column(name="PRIORITY")
	private int priority = ChannelPriorityEnum.NORMAL.ordinal();
	
	@Column(name="CREATED_TIME")
	private Date createdTime;
	
	// created user id ( int )
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Transient
	private List<Tag> tags;
	
	@Transient
	private List<User> users;
	
	@Transient
	private List<Message> messages;

	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String userId) {
		this.createdBy = userId;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	// utility methods
	public String toJson(){
        Gson gson = new Gson();
        return gson.toJson( this);
    }

    public static Channel fromJson( String json ){
        Gson gson = new Gson();
        return gson.fromJson(json, Channel.class);
    }
}
