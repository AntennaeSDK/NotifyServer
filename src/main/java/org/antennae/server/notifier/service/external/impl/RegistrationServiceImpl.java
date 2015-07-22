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

package org.antennae.server.notifier.service.external.impl;

import javax.inject.Inject;

import org.antennae.server.notifier.service.external.IRegistrationService;
import org.antennae.server.notifier.service.internal.IAppInfoService;
import org.antennae.server.notifier.service.internal.IDeviceInfoService;
import org.antennae.transport.AppDetails;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements IRegistrationService {

	@Inject
	private IAppInfoService appInfoSvc;
	
	@Inject
	private IDeviceInfoService deviceInfoSvc;

	/**
	 *  1. check whether the device is registered
	 *  2. if not register the device
	 *  3. check whether the app is registered
	 *  4. if not register the app
	 *  5. if the app is registered, update the app info if needed
	 */
	@Override
	public void register(AppDetails appDetails) {
		
		if( appDetails == null ){
			throw new NullPointerException("AppDetails cannot be null");
		}

		deviceInfoSvc.addDeviceInfo(appDetails.getDeviceInfo());
		
		// set the deviceId 
		int deviceId = appDetails.getDeviceInfo().getId();
		appDetails.getAppInfo().setDeviceId(deviceId);
		
		appInfoSvc.addAppInfo( appDetails.getAppInfo()); 
	}

}
