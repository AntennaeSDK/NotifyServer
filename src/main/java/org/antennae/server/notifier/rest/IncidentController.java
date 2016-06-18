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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.antennae.common.entitybeans.Channel;
import org.antennae.common.entitybeans.Message;
import org.antennae.common.entitybeans.User;
import org.antennae.server.notifier.service.external.IIncidentService;
import org.antennae.server.notifier.service.external.IUserService;
import org.antennae.server.notifier.transport.ChatWebSocketMessage;
import org.antennae.server.notifier.utils.conversion.XMessageUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncidentController {
	
	@Inject
	private IIncidentService incidentSvc;
	
	@Inject
	private IUserService userSvc;

	@RequestMapping(value="/api/incidents", method=RequestMethod.POST )
	@ResponseBody
	public Channel addIncident( @RequestBody String json ){
		Channel channel=null;
		
		try {
			
			String jsonStr = URLDecoder.decode(json, "UTF8");
			if( jsonStr.endsWith("}=") ){
				jsonStr = jsonStr.replace("}=", "}");
			}
			
			channel = Channel.fromJson(jsonStr);
			incidentSvc.addIncident(channel);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return channel;
	}
	
	@RequestMapping(value="/api/incidents", method=RequestMethod.GET )
	@ResponseBody
	public List<Channel> list(){
		return incidentSvc.getIncidents();
	}
	
	@RequestMapping(value="/api/incidents/{incidentId}", method=RequestMethod.GET )
	@ResponseBody
	public Channel getIncident( @PathVariable("incidentId") int incidentId){
		return incidentSvc.getIncident(incidentId);
	}
	
	@RequestMapping(value="/api/incidents/{incidentId}/messages", method=RequestMethod.GET )
	@ResponseBody
	public List<ChatWebSocketMessage> getMessages(@PathVariable("incidentId") int incidentId ){
		
		List<Message> messages = incidentSvc.getMessages(incidentId);
		
		// collect all userIds
		Set<String> logins = new HashSet<String>();
		for( Message m : messages ){
			logins.add(m.getSenderId());
		}
		
		List<String> loginIds = new ArrayList<String>();
		loginIds.addAll(logins);
		
		// get the users details
		List<User> fullusers = userSvc.getUsersForLoginIds(loginIds);
		
		// convert <Message> to <XMessage> 
		List<ChatWebSocketMessage> msgs = XMessageUtils.convertMessageToXWebSocketMessage(messages);
		
		for( ChatWebSocketMessage m : msgs){
			for( User u : fullusers ){
				if( m.getSenderId().equals( u.getUserId()) ){
					m.setSenderName(u.getName());
				}
			}
		}
		
		return msgs;
	}
}
