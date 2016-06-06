package org.antennae.server.notifier.ws;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.logging.Logger;

public class MyHandler extends TextWebSocketHandler {

    private Logger logger = Logger.getLogger(MyHandler.class.getName());

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

        logger.info("message: " + message );
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("connection established");


    }
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.info("connection error");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("connection closed");
    }

}