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

package org.antennae.server.notifier.config;

import java.io.IOException;

import javax.net.ssl.SSLSocketFactory;

import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GcmXmppConfig {

	@Bean
	public XMPPTCPConnection getGcmConnection() {

		String GCM_SERVER = "gcm.googleapis.com";
		int GCM_PORT = 5235;

		// username = GCM_PROJECT_ID + @gcm.googleapis.com
		final String userName = "549760952886" + "@gcm.googleapis.com";

		// password = GCM_SERVER_KEY
		final String password = "AIzaSyCsjVuDvWlm6ZGeB52LVYXeRXTwxyjFzWs";

		XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();

		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
														.setServiceName(GCM_SERVER)
														.setHost(GCM_SERVER)
														.setCompressionEnabled(false)
														.setPort(GCM_PORT)
														.setConnectTimeout(30000)
														.setSecurityMode(SecurityMode.disabled)
														.setSendPresence(false)
														.setSocketFactory(SSLSocketFactory.getDefault())
														.build();

		XMPPTCPConnection connection = new XMPPTCPConnection(config);

		// disable Roster as I don't think this is supported by GCM
		Roster roster = Roster.getInstanceFor(connection);
		roster.setRosterLoadedAtLogin(false);

		// logger.info("Connecting...");
		try {
			connection.connect();
		} catch (SmackException | IOException | XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// connection.addConnectionListener(new LoggingConnectionListener());

		// Handle incoming packets
		// connection.addAsyncStanzaListener(new MyStanzaListener() , new
		// MyStanzaFilter() );

		// Log all outgoing packets
		// connection.addPacketInterceptor(new MyStanzaInterceptor(), new
		// MyStanzaFilter() );

		try {
			connection.login(userName, password);
		} catch (XMPPException | SmackException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}
