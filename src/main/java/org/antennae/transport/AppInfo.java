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

package org.antennae.transport;

import com.google.gson.Gson;

public class AppInfo {

    private int appVersion;
    private String appId;
    private long firstInstallTime;
    private long lastUpdateTime;
    
    
	public int getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(int appVersion) {
		this.appVersion = appVersion;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public long getFirstInstallTime() {
		return firstInstallTime;
	}
	public void setFirstInstallTime(long firstInstallTime) {
		this.firstInstallTime = firstInstallTime;
	}
	public long getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson( this);
    }

    public static AppInfo fromJson( String json ){
        Gson gson = new Gson();
        return gson.fromJson(json, AppInfo.class);
    }

}
