package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.exceptions.EmailError;
import com.jmurilloc.pfc.scouts.services.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService
{
    
    @Value( "${spring.mail.username}" )
    private String senderEmail;
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    
    @Override
    public void sendSimpleEmail( String to, String subject, String text )
    {
        try
        {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo( to );
            message.setSubject( subject );
            message.setText( text );
            message.setFrom( senderEmail );
            
            javaMailSender.send( message );
        }
        catch ( Exception e )
        {
            throw new EmailError( "Error sending email from " + senderEmail + " to " + to );
        }
    }
}
