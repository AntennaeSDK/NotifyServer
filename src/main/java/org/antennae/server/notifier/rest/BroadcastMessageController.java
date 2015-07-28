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

package org.antennae.server.notifier.rest;

import java.util.List;

import javax.inject.Inject;

import org.antennae.server.notifier.gcm.xmpp.GcmXmppClient;
import org.antennae.server.notifier.service.external.IRegistrationService;
import org.antennae.transport.AppDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BroadcastMessageController {

	@Inject
	private GcmXmppClient gcmClient;
	
	@Inject
	private IRegistrationService registrationService;
	
	@RequestMapping(value="/api/messages", method=RequestMethod.POST)
	@ResponseBody
	public String sendMessage( @RequestBody  String message){
		
		// first get the list of registered devices for this app.
		List<AppDetails> appDetails = registrationService.getAllRegistrations();
		
		// send the message
		for( AppDetails app : appDetails ){
			String gcmRegistrationId = app.getAppInfo().getGcmRegistrationId();
			gcmClient.sendDownstreamMessage(message, gcmRegistrationId);
		}
		
		return "message sent";
	}
}
