package org.antennae.server.notifier.ws;

import org.antennae.common.messages.ClientMessage;
import org.antennae.common.messages.ClientMessageWrapper;
import org.antennae.common.messages.ServerMessage;

/**
 * Created by snambi on 6/22/16.
 */
public interface IClientHandler {

    // mostly received from clients to be processed in the server
    public void receiveFromClient(ServerMessage serverMessage );

    // to be sent to the client
    public void sendToClient(ClientMessageWrapper clientMessage);

}
