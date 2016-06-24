package org.antennae.server.notifier.rest;

import org.antennae.common.entitybeans.Channel;
import org.antennae.common.messages.ClientMessage;
import org.antennae.common.messages.ClientMessageWrapper;
import org.antennae.server.notifier.ws.ClientTextWebSocketHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by snambi on 6/23/16.
 */
@Controller
public class MessageController {

    @Inject
    ClientTextWebSocketHandler clientTextWebSocketHandler;

    @RequestMapping(value="/api/messages", method= RequestMethod.POST )
    @ResponseBody
    public ClientMessage sendDownstreamMessage(@RequestBody String json ){
        ClientMessage clientMessage=null;

        try {

            String jsonStr = URLDecoder.decode(json, "UTF8");
            if( jsonStr.endsWith("}=") ){
                jsonStr = jsonStr.replace("}=", "}");
            }

            clientMessage = ClientMessage.fromJson(jsonStr);

            ClientMessageWrapper wrapper = new ClientMessageWrapper();
            wrapper.setClientMessage( clientMessage );

            clientTextWebSocketHandler.sendToClient( wrapper );

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return clientMessage;
    }
}
