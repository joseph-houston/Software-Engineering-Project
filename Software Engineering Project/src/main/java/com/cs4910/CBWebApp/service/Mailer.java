package com.cs4910.CBWebApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

// This class will be called by monitor
//@Service("mailerService")
public class Mailer {
    private String recipientAddress = "";
    private String subject;
    private String message;	
	
	@Autowired
	private JavaMailSender mailSender;
	// read scheduled reports file

	public void sendReports(){
		// for each record, 
		// get the project id,name from this file
		// get the email from this file
		// create a report object for the name/id above
		// get the report email text and
		// create a simple e-mail object
		
		recipientAddress = "gathenje@yahoo.com";
		subject = "Testing scheduling and email";
		message = "Oh! if you see this, smile!!:) this feature works!!!";
		 
		// prints debug info
		System.out.println("To: " + recipientAddress);
		System.out.println("Subject: " + subject);
		System.out.println("Message: " + message);
		 
		// creates a simple e-mail object
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message);
		 
		// sends the e-mail
		mailSender.send(email);
	}

}
