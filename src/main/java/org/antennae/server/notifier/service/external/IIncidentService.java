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

package org.antennae.server.notifier.service.external;

import java.util.List;

import org.antennae.common.entitybeans.Channel;
import org.antennae.common.entitybeans.Message;

public interface IIncidentService {

	public void addIncident( Channel channel );
	public void updateIncident( Channel channel );
	public Channel getIncident( int channelId );
	public void deleteIncident( int channelId );
	
	public List<Channel> getIncidents();
	public List<Channel> getIncidents( String userId );
	
	public List<Message> getMessages( int channelId );
}
