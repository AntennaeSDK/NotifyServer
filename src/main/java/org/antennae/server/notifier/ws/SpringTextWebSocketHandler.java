package org.antennae.server.notifier.ws;

import org.antennae.common.entitybeans.ChannelClient;
import org.antennae.common.beans.ClientStatusEnum;
import org.antennae.common.entitybeans.Message;
import org.antennae.common.entitybeans.User;
import org.antennae.server.notifier.service.external.IUserService;
import org.antennae.server.notifier.service.internal.IChannelClientService;
import org.antennae.server.notifier.service.internal.IMessageService;
import org.antennae.server.notifier.transport.ChatWebSocketMessage;
import org.antennae.server.notifier.utils.conversion.XMessageUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpringTextWebSocketHandler extends TextWebSocketHandler {

    private Logger logger = Logger.getLogger(SpringTextWebSocketHandler.class.getName());

    private Set<WebSocketSession> userSessions = Collections.synchronizedSet(new HashSet<WebSocketSession>());

    @Inject
    IChannelClientService channelClientSvc;

    @Inject
    IMessageService messageSvc;

    @Inject
    IUserService userSvc;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("connection established : " + session.getId());
        userSessions.add(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.info("connection error");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("connection closed: " + session.getId() + ", status :" + status.toString());
        if( userSessions.contains(session) ){
            userSessions.remove(session);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

        logger.info("message: " + message);

        if (message == null) {
            return;
        }

        // check whether the message is first message
        ChatWebSocketMessage wsMsg = null;
        try {
            wsMsg = ChatWebSocketMessage.fromJson(message.getPayload());
        }catch( Throwable throwable){
            // if there is an error parsing the incomin message.
            // simply ignore
            logger.log(Level.CONFIG, "unable to parse the incoming message. ignoring ...");
            return;
        }

        logger.info("SessionId: " + session.getId() + ", message: " + wsMsg.toJson());

        if (channelClientSvc != null) {

            if (wsMsg.isFirstMessage()) {

                // Register the client for the incident if it is a first message

                ChannelClient client = new ChannelClient();
                client.setChannelId(wsMsg.getChannelId());
                client.setToken(wsMsg.getToken());
                client.setCreatedTime(wsMsg.getCreatedTime());
                client.setClientStatus(ClientStatusEnum.LIVE);
                client.setClientType(wsMsg.getClientType());

                channelClientSvc.addClient(client);

            } else {
                // persist the message
                Message msg = XMessageUtils.convertXWebSocketMessageToMessage(wsMsg);
                if (msg.getSentTime() == null) {
                    Date sentTime = Calendar.getInstance().getTime();
                    msg.setSentTime(sentTime);
                }
                messageSvc.addMessage(msg);

                // TODO: find clients that subscribe to the incident

                // send the message to the subscribers
                ChatWebSocketMessage xwsMsg = XMessageUtils.convertMessageToXWebSocketMessage(msg);
                if (wsMsg.getSenderName() != null) {
                    xwsMsg.setSenderName(wsMsg.getSenderName());
                } else {
                    User u = userSvc.getUserByLoginId(wsMsg.getSenderId());
                    xwsMsg.setSenderName(u.getName());
                }

                for (WebSocketSession s : userSessions) {

                    try {

                        if( s.isOpen() && !session.getId().equals(s.getId()) ){
                            s.sendMessage(xwsMsg.toSpringTextWebSocketMessage());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}