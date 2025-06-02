package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.exceptions.EmailError;
import com.jmurilloc.pfc.scouts.services.interfaces.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService
{
    
    @Value( "${spring.mail.username}" )
    private String senderEmail;
    
    @Value( "${spring.mail.username.scoutsgrup}" )
    private String mailScoutsGroup;
    
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
            log.info( "Mensaje con subject {} enviado a {}", subject, to );
        }
        catch ( Exception e )
        {
            throw new EmailError( "Error sending email from " + senderEmail + " to " + to );
        }
    }
    
    
    @Override
    public void sendEmailWithBackup( String from, String subject, String text )
    {
        try
        {
            sendSimpleEmail( mailScoutsGroup, subject, text + "\n\n Correo de contacto: " + from );
            log.info( "Enviando respaldo a: {} ", from );
            sendSimpleEmail( from, "Respaldo mensaje de contacto", "RESPALDO de: \n" + text + "\n\nRecibirá una respuesta de contacto en breve." );
        }
        catch ( Exception e )
        {
            throw new EmailError( "Error sending email from " + senderEmail + " to " + from );
        }
    }
}
