package org.antennae.server.notifier.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpUtils;

import org.antennae.server.notifier.rest.RestClient;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
public class NotifierDashboardController {
	
	Logger logger = LoggerFactory.getLogger(NotifierDashboardController.class);

	@RequestMapping("/notifier")
	public String notifierPage(){
		return "notifier";
	}
	
	@RequestMapping(value="/resource",method=RequestMethod.GET )
	@ResponseBody
	public Map<String,Object> getValues(){
		
		Map<String,Object> model = new HashMap<String, Object>();
		
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "hello");
		
		return model;
	}
	
	
	@RequestMapping(value="/notification", method=RequestMethod.POST)
	@ResponseBody
	public void send( @RequestBody String json){
		
		String alertStr;
		try {
			alertStr = URLDecoder.decode(json, "UTF8");
			alertStr = alertStr.replace("}=", "}");
			Alert alert = Alert.fromJson(alertStr);
			
			sendNotification(alert);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendNotification( Alert alert ) throws Exception{
		
		RestClient client = new RestClient("https://api.parse.com");
		client.addDefaultRestHeaders();
		client.addHeader("X-Parse-Application-Id", "N2aksV1jywajiRKOV92u42Scz3Q5RGFmOuW6Iwtx");
		client.addHeader("X-Parse-REST-API-Key", "A7lXu86mtdBTxfxOJnLSeMuaahLG4VC0uL1MppUG");
		
		ParsePush push = new ParsePush(alert);
		String response = client.POST("/1/push", push.toJson());
				
		if( response != null ){
			JSONObject json = new JSONObject(response);
			
			boolean failure = false;
			
			if( json.has("result")){
				// check whether success
				if( json.getBoolean("result") == false ){
					// failure
					failure =true;
				}else{
					System.out.println("Push result: " + response);
				}
			}else{
				// definitely failure
				failure =true;
			}
			
			if( failure == true ){
				logger.error( "ParsePush failed for " + push.toJson() + ", ERROR :" + response);
				System.out.println("ParsePush failed for " + push.toJson() + ", ERROR :" + response);
				throw new Exception("Parse push failed");
			}
		}
	}
	
	public static enum AlertSeverityEnum{
		NORMAL,
		UNDER_WATCH,
		ELEVATED,
		HIGH,
		SEVERE
	}
	
	public static class Alert{
		
		private String title;
		private String message;
		private String severity;
		private String action;
		private Date date= new Date();
		private long id = date.getTime();
		
		public Alert( String title, String msg, String severity, String action){
			this.title = title;
			this.message = msg;
			this.severity = severity;
			this.action = action;
		}
		
		public Alert(){
		}
		
		public String toJson(){
			Gson gson = new Gson();
			return gson.toJson(this, Alert.class );
		}
		
		public static Alert fromJson( String json ){
			Gson gson = new Gson();
			return gson.fromJson( json, Alert.class);
		}
		
		public String toString(){
			StringBuilder sb = new StringBuilder();
			sb.append("title=");
			sb.append(title);
			sb.append(",message=");
			sb.append(message);
			sb.append(",severity=");
			sb.append(severity);
			sb.append(",action=");
			sb.append(action);
			sb.append(",time=");
			sb.append(date.toString());
			return sb.toString();
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getSeverity() {
			return severity;
		}

		public void setSeverity(String severity) {
			this.severity = severity;
		}

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
	}
	
	public static class ParsePush{
		
		private Where where;
		private Data data;
		
		public ParsePush( Alert alert){
			this.data = new Data(alert);
			//this.where = "{ \"deviceType\" : { \"$in\" : [ \"ios\", \"android\"] } }";
			this.where = new Where();
		}
		
		public ParsePush(Alert alert, Where where){
			this.data = new Data(alert);
			this.where = where;
		}
		
		public String toJson(){
			Gson gson = new Gson();
			return gson.toJson(this, ParsePush.class);
		}
	}
	
	public static class Data{
		private String alert;
		private Alert notification;
		
		public Data( Alert alert ){
			
			// add severity to alert
			String severityStr =null;
			if( alert.getSeverity().equals(AlertSeverityEnum.SEVERE.name())){
				severityStr = "P0";
			}
			if( alert.getSeverity().equals(AlertSeverityEnum.HIGH.name())){
				severityStr = "P1";
			}
			if( alert.getSeverity().equals(AlertSeverityEnum.ELEVATED.name())){
				severityStr = "P2";
			}
			if( alert.getSeverity().equals(AlertSeverityEnum.UNDER_WATCH.name())){
				severityStr = "P3";
			}
			if( alert.getSeverity().equals(AlertSeverityEnum.NORMAL.name())){
				severityStr = "P4";
			}
			
			this.alert = severityStr + ":" + alert.getTitle();
			this.notification = alert;
		}
	}
	
	public static class Where{
		private DeviceType deviceType = new DeviceType();
	}
	
	public static class DeviceType{
		String[] $in = {"ios", "android" };
		
		public String toJson(){
			Gson gson = new Gson();
			return gson.toJson(this, ParsePush.class);
		}
	}
	
}
