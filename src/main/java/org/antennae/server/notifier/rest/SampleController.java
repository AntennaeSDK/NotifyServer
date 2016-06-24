package org.antennae.server.notifier.rest;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by snambi on 6/24/16.
 */
@Controller
public class SampleController {

    @RequestMapping(value="/api/sample", method= RequestMethod.GET )
    @ResponseBody
    public String getSample(){

        Message message = new Message();
        message.id = "243522";
        message.type = "TEXT";
        message.sender = "N1";

        Message.Body body = new Message.Body();
        body.text = "Sample Response from API @ " + getCurrentTimestamp();

        message.body = body;

        return message.toJson();
    }

    private String getCurrentTimestamp(){

        long time = Calendar.getInstance().getTimeInMillis();
        Date date = new Date(time);

        return date.toString();
    }

    public static class Message{
        String id;
        String type;
        String version;
        String sender;
        String username;
        Body body;

        public static class Body{
            String text;
        }

        public String toJson(){
            Gson gson = new Gson();
            String result = gson.toJson(this);
            return result;
        }
        public static Message fromJson( String json ){
            Gson gson = new Gson();
            Message message = gson.fromJson( json, Message.class);
            return message;
        }
    }
}
