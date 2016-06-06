package org.antennae.server.notifier.ws;

import org.springframework.web.socket.WebSocketMessage;

/**
 * Created by snambi on 6/6/16.
 */
public class SpringWebSocketMessage implements WebSocketMessage {
    @Override
    public Object getPayload() {
        return null;
    }

    @Override
    public int getPayloadLength() {
        return 0;
    }

    @Override
    public boolean isLast() {
        return false;
    }
}
