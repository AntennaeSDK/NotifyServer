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

package org.antennae.server.notifier.gcm.xmpp;

import java.io.IOException;

import javax.net.ssl.SSLSocketFactory;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.DefaultExtensionElement;
import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.provider.ExtensionElementProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.xmlpull.v1.XmlPullParser;


public class GcmXmppClient {
	
	private Logger logger = Logger.getLogger(GcmXmppClient.class);
	
	public static final String GCM_ELEMENT_NAME = "gcm";
	public static final String GCM_NAMESPACE = "google:mobile:data";
	public static final String GCM_PROD_HOST ="gcm.googleapis.com";
	public static final int GCM_PROD_PORT = 5235;
	
	private String gcmHost;
	private int gcmPort;
	private String gcmUser;
	private String gcmPassword;
	private String gcmProjectId;
	
	public GcmXmppClient( String user, String password, String projectId  ){
		this( GCM_PROD_HOST, GCM_PROD_PORT, user, password, projectId);
	}

	public GcmXmppClient( String host, int port, String user, String password, String projectId ){
		
		if( user == null || user.trim().equals("") ){
			throw new NullPointerException("user cannot be null");
		}
		if( password == null || password.trim().equals("")){
			throw new NullPointerException("password cannot be null");
		}
		if( projectId == null || projectId.trim().equals("")){
			throw new NullPointerException("Gcm ProjectId cannot be null or empty");
		}
		
		
		if( host == null || host.trim().equals("")){
			throw new NullPointerException("host cannot be null");
		}
		if( port < 1000 ){
			throw new IllegalArgumentException("Port cannot be less than 1000");
		}
		
		this.gcmHost = host;
		this.gcmPort = port;
		this.gcmUser = user;
		this.gcmPassword = password;
		this.gcmProjectId = projectId;
		
		ProviderManager.addExtensionProvider(GCM_ELEMENT_NAME, GCM_NAMESPACE, new  ExtensionElementProvider<ExtensionElement>() {
            @Override
            public DefaultExtensionElement parse(XmlPullParser parser,int initialDepth) throws org.xmlpull.v1.XmlPullParserException,
            IOException {
                String json = parser.nextText();
                return new GcmPacketExtension(json);
            }
        });
	}
	
	
	public void connect(){

		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
														.setServiceName( gcmHost )
														.setHost( gcmHost )
														.setCompressionEnabled(false)
														.setPort( gcmPort )
														.setConnectTimeout(30000)
														.setSecurityMode(SecurityMode.disabled)
														.setSendPresence(false)
														.setSocketFactory(SSLSocketFactory.getDefault())
														.build();

		XMPPTCPConnection connection = new XMPPTCPConnection(config);

		// disable Roster as I don't think this is supported by GCM
		Roster roster = Roster.getInstanceFor(connection);
		roster.setRosterLoadedAtLogin(false);

		logger.info("Connecting to GCM server...");
		
		try {
			connection.connect();
		} catch (SmackException | IOException | XMPPException e) {
			e.printStackTrace();
		}

		connection.addConnectionListener( new LoggingConnectionListener() );

		// Handle incoming packets
		connection.addAsyncStanzaListener(new GcmStanzaListener() , new GcmStanzaFilter( this.gcmProjectId ) );

		// Log all outgoing packets
		connection.addPacketInterceptor(new GcmStanzaInterceptor(), new GcmStanzaFilter( this.gcmProjectId ) );

		try {
			connection.login(gcmUser, gcmPassword);
		} catch (XMPPException | SmackException | IOException e) {
			e.printStackTrace();
		}
	}
}
