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

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AUTH_TOKEN")
public class AuthToken {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="AUTH_TOKEN")
	private String authToken;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="DEVICE_ID")
	private String deviceId;
	
	@Column(name="APP_ID")
	private String appId;
	
	@Column(name="APP_VERSION")
	private String appVersion;
	
	@Column(name="CREATION_DATE")
	private Date creationDate;
	
	@Column(name="EXPIRY_DATE")
	private Date expiryDate;
	
	@Column(name="STATUS")
	private AuthTokenStatusEnum status;

	
	// additional helper methods
	public boolean isValid(){
		boolean result = false;
		
		if( status == AuthTokenStatusEnum.VALID ){
			result = true;
		}
		return result;
	}
	public boolean isExpired(){
		boolean result = false;
		
		Date currentTime = Calendar.getInstance().getTime();
		if( currentTime.before(creationDate) && 
				currentTime.after(expiryDate) ){
			result = true;
		}
		return result;
	}
	
	public boolean isActive(){
		boolean result = false;
		if( !isExpired() && isValid()){
			result = true;
		}
		return result;
	}
	
	// generated getters and setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public AuthTokenStatusEnum getStatus() {
		return status;
	}
	public void setStatus(AuthTokenStatusEnum status) {
		this.status = status;
	}
	
}
