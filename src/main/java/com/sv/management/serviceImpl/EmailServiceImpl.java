package com.sv.management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sv.management.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService  {
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(String toEmail, String password) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject("Your Account Password");
		message.setText("Welcome to our application!\nYour password is: " + password);

		javaMailSender.send(message);
	}
}
