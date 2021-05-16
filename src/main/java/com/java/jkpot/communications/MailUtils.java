package com.java.jkpot.communications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailUtils {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendMail(String to, String subject, String description) {
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(description);

		javaMailSender.send(msg);
	}
}