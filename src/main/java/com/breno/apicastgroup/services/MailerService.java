package com.breno.apicastgroup.services;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.breno.apicastgroup.entities.Employee;

@Service
public class MailerService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${default.sender}")
	private String sender;
	
	public void sendMailWithFile(Employee employee, String filePath) {
				
		try {			
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	        helper.setFrom(sender);
	        helper.setTo(employee.getEmail());
	        helper.setSubject("Cadastro realizado com sucesso");
	        helper.setText(employee.toString());
	        helper.addAttachment("qrcode.png", new File(filePath));
	        javaMailSender.send(mimeMessage);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    } 
	}
}
