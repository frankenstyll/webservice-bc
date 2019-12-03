package com.ktb.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailServicesImpl implements SendEmailServices{

	@Autowired
	MailSender mailSender;
	
	@Override
	public Map<String, Object> sendTextEmail(SimpleMailMessage message) {
		Map<String,Object> response = new HashMap<String, Object>();
		try {
			
			if (null == message) {

				SimpleMailMessage messageSimple = new SimpleMailMessage();
	            String from = "nontapap.th@gmail.com";
	            String to = "nontapap.th@gmail.com";
	            String subject = "HELLO OTP";
	            String body = "Hello world OTP";
	            messageSimple.setFrom(from);
	            messageSimple.setTo(to);
	            messageSimple.setSubject(subject);
	            messageSimple.setText(body);
	            mailSender.send(messageSimple);
	            
			}else {
				mailSender.send(message);
			}
			
			//mean success
			response.put("status", "0");
			
		}catch (Exception e) {
			e.printStackTrace();
			response.put("status", "1");
			response.put("message", e.getMessage());
		}
		
		return response;
	}

	@Override
	public Map<String, Object> sendHtmlEmail(SimpleMailMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

}
