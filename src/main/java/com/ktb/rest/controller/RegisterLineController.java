package com.ktb.rest.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.ktb.model.Register;
import com.ktb.services.SendEmailServices;

@RestController
public class RegisterLineController {

	private static final Logger log = LoggerFactory.getLogger(RegisterLineController.class);
	
	@Autowired 
	SendEmailServices sendMailServices;
	
	@GetMapping("/sendMail")
	public @ResponseBody String sendMail(){
//		private static void getEmployees()
//		{
//		    final String uri = "http://localhost:8080/springrestexample/employees.xml";
//
//		    RestTemplate restTemplate = new RestTemplate();
//		    String result = restTemplate.getForObject(uri, String.class);
//
//		    System.out.println(result);
//		}
		log.info("sendmail info");
		return new Gson().toJson(sendMailServices.sendTextEmail(null));
	}
	

	@PostMapping("/requestOTP")
	public @ResponseBody String requestOTP(@ModelAttribute Register register) {
		log.info("requestOTP info");
		try {
			//url for call webservice for push line message
			URL url = new URL("https://glacial-peak-48383.herokuapp.com/bcbot/pushMessage");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			String input = "{\"userId\":\"Ue0f8a270ffea40064588037b51272d28\","
					+ "\"messages\": ["
					+ "{\"type\": \"text\", 	\"text\": \"เรียน BC XXX XXXX\"} ,"
					+ "{\"type\": \"text\", \"text\": \"ลูกค้าของท่านเลยกำหนดระยะเวลา\"}, "
					+ "{\"type\": \"text\", \"text\": \"รบกวนติดต่อสอบถาม\"} ]"
					+ "}";
			log.info("call create json text");
//			String input = createJsonText();
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
		return "";
	}
}
