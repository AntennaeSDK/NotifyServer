package org.antennae.server.notifier.ws;

import org.antennae.common.messages.ServerMessage;

/**
 * Created by snambi on 6/22/16.
 */
public interface IClientHandler {

    public void process( ServerMessage message );

}
