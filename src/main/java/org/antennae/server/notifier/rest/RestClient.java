package org.antennae.server.notifier.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestClient {

	private String server=null;
	private RestTemplate rest;
	private HttpHeaders headers;
	private HttpStatus status;
	
	public RestClient(String hostname){
		this(hostname, 0);
	}
	
	public RestClient( String hostname, int portnumer){
				
		if( portnumer == 0 || portnumer == 80){
			this.server = hostname;
		}else{
			this.server = hostname + ":" + portnumer;
		}
		
		this.rest = new RestTemplate();
		this.headers = new HttpHeaders();
	}
	
	public void addDefaultRestHeaders(){
		headers.add("Content-Type", "application/json");
		headers.add("Accept", "*/*");
	}
	
	public void addHeader(String header, String value){
		headers.add(header, value);
	}
	
	public String GET(String url){
		
		HttpEntity<String> requestEntity = new HttpEntity<String>("",headers);
		ResponseEntity<String> responseEntity = rest.exchange(server, HttpMethod.GET, requestEntity, String.class);
		
		this.setStatus(responseEntity.getStatusCode());
		
		return responseEntity.getBody();
	}
	
	public String POST(String url, String json){
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(json,headers);
		ResponseEntity<String> responseEntity = rest.exchange(server+url, HttpMethod.POST, requestEntity, String.class);
		
		this.setStatus(responseEntity.getStatusCode());
		
		return responseEntity.getBody();
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
