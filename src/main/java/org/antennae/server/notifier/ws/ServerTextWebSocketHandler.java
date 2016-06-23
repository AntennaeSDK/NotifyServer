package org.antennae.server.notifier.ws;

import org.antennae.common.messages.ServerMessage;
import org.antennae.common.messages.ServerTrackedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by snambi on 6/22/16.
 */
public class ServerTextWebSocketHandler extends TextWebSocketHandler implements IServerHandler{

    Logger logger = LoggerFactory.getLogger(ServerTextWebSocketHandler.class);

    private Map<String,WebSocketSession> serverSessions = new ConcurrentHashMap<String,WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Server connection established " + session.getId());
        serverSessions.put(session.getId(), session );
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("Server connection closed " + session.getId());
        serverSessions.remove(session.getId());
    }

    @Override
    public void processRequestResponse(String wsSessionId, ServerMessage message) {

        // TODO: get the right session that processes this message
        ServerTrackedMessage trackedMessage = new ServerTrackedMessage();
        trackedMessage.setServerMessage(message);
        trackedMessage.setSessionId(wsSessionId);

        Set<String> sessionKeys = serverSessions.keySet();
        for( String key : sessionKeys ){

            WebSocketSession serverSession =  serverSessions.get(key);

            if( serverSession.isOpen() ){

                TextMessage wsMessage = new TextMessage( trackedMessage.toJson());
                try {
                    serverSession.sendMessage( wsMessage );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void processPubSub(ServerMessage message) {

    }
}
