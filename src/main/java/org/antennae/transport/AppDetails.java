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

import org.antennae.server.notifier.entities.AppInfo;
import org.antennae.server.notifier.entities.DeviceInfo;

import com.google.gson.Gson;

public class AppDetails {

	//private String gcmRegistrationId;
    private DeviceInfo deviceInfo;
    private AppInfo appInfo;
    
//	public String getGcmRegistrationId() {
//		return gcmRegistrationId;
//	}
//	public void setGcmRegistrationId(String gcmRegistrationId) {
//		this.gcmRegistrationId = gcmRegistrationId;
//	}
	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	public AppInfo getAppInfo() {
		return appInfo;
	}
	public void setAppInfo(AppInfo appInfo) {
		this.appInfo = appInfo;
	}

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson( this);
    }

    public static AppDetails fromJson( String json ){
        Gson gson = new Gson();
        return gson.fromJson(json, AppDetails.class);
    }

}
