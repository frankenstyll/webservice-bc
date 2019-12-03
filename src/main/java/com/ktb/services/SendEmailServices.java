package com.ktb.services;

import java.util.Map;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

public interface SendEmailServices {

	public Map<String,Object> sendTextEmail(SimpleMailMessage message);
	public Map<String,Object> sendHtmlEmail(SimpleMailMessage message);
}
