package com.ktb.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.ktb.model.Messages;
import com.ktb.model.TextMessage;

public class TestApplication {
	
	@Autowired
	private static RestTemplate restTemplate;
	
	@Autowired
	private static HttpHeaders header;
	
	@Autowired
	static JavaMailSender mailSender;
	
	private static final Logger log = LoggerFactory.getLogger(TestApplication.class);
	

	public static void main(String[] args) {
		
//		broadcastMessage();
//		pushMessage();
		sendEmail();

	}
	
	private static void sendEmail() {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo("nontapap.th@gmail.com");
	        msg.setSubject("Testing from Spring Boot");
	        msg.setText("Hello World \n Spring Boot Email");

	        mailSender.send(msg);
	        //.send(msg);
	        
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String createJsonText() {
		
		TextMessage textMessage = new TextMessage();
		textMessage.setUserId("Ue0f8a270ffea40064588037b51272d28");
		List<Messages> lstTextMessage = new ArrayList<Messages>();

		Messages m1 = new Messages();
		m1.setType("text");
		m1.setText("YYYYYYY");
		lstTextMessage.add(m1);
		
		Messages m2 = new Messages();
		m2.setType("text");
		m2.setText("เรียน BC Jack'n Jill ลูกค้าของท่านกำลังจะครบรอบ รบกวนติดต่อ");
		lstTextMessage.add(m2);
		
		Messages m3 = new Messages();
		m3.setType("text");
		m3.setText("XXXXXXX");
		lstTextMessage.add(m3);
		
		textMessage.setMessages(lstTextMessage);
		
		Gson gson = new Gson();    
	    return gson.toJson(textMessage);
	}
	
	private static void pushRestTemplate() {
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject personJsonObject = new JSONObject();
	    personJsonObject.put("id", 1);
	    personJsonObject.put("name", "John");
		String urlStr = "https://glacial-peak-48383.herokuapp.com/bcbot/pushMessage";
		String input = createJsonText();
		String reResponse = restTemplate.exchange("", HttpMethod.POST
				, null, new String().getClass()).getBody();
		log.info(reResponse);
	}
	
	private static void pushMessage() {
		
		try {
			//url for call webservice for push line message
			URL url = new URL("https://glacial-peak-48383.herokuapp.com/bcbot/pushMessage");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
//			String input = "{\"userId\":\"Ue0f8a270ffea40064588037b51272d28\","
//					+ "\"messages\": ["
//					+ "{\"type\": \"text\", 	\"text\": \"เรียน BC XXX XXXX\"} ,"
//					+ "{\"type\": \"text\", \"text\": \"ลูกค้าของท่านเลยกำหนดระยะเวลา\"}, "
//					+ "{\"type\": \"text\", \"text\": \"รบกวนติดต่อสอบถาม\"} ]"
//					+ "}";
			
			log.info("call create json text");
			String input = createJsonText();
			log.info(input);
			
			log.info("get output stream");
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			
			log.info("");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();

		  } catch (MalformedURLException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		 }
	}
	
	private static void broadcastMessage() {
		
		try {
			//url for call webservice for push line message
			URL url = new URL("https://glacial-peak-48383.herokuapp.com/bcbot/broadcastMessage");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			log.info("call create json text");
			
			TextMessage textMessage = new TextMessage();
			List<Messages> lstTextMessage = new ArrayList<Messages>();
			
			Messages m2 = new Messages();
			m2.setType("text");
			m2.setText("เรียน BC Jack'n Jill ลูกค้าของท่านกำลังจะครบรอบ รบกวนติดต่อ");
			lstTextMessage.add(m2);
			textMessage.setMessages(lstTextMessage);

			Gson gson = new Gson();    
			String input = gson.toJson(textMessage);
			log.info(input);
			
			log.info("get output stream");
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();

		  } catch (MalformedURLException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		 }
	}
	
	private static String testCall() {
		
//		RestTemplate restTemplate = new RestTemplate();
//	     
//	    String baseUrl = "https://glacial-peak-48383.herokuapp.com/bcbot/broadcastMessage";
//	    URI uri = new URI(baseUrl);
//	     
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.set("X-COM-PERSIST", "true");  
//	    headers.set("X-COM-LOCATION", "USA");
//	 
//	    HttpEntity<Employee> requestEntity = new HttpEntity<>(null, headers);
//	 
//	    ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
//	     
//	    //Verify request succeed
//	    Assert.assertEquals(200, result.getStatusCodeValue());
//	    Assert.assertEquals(true, result.getBody().contains("employeeList"));
		
		return "";
	}
	
}


