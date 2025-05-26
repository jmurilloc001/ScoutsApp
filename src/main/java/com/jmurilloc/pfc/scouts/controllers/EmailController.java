package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.services.interfaces.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EmailController
{
    
    @Autowired
    private EmailService emailService;
    
    
    /**
     * Endpoint to send a simple email.
     *
     * @param to
     *         Recipient email address
     * @param subject
     *         Email subject
     * @param text
     *         Email body
     * @return Response message
     */
    @GetMapping( "/send-email" )
    public ResponseEntity<String> sendEmail( @RequestParam String to, @RequestParam String subject, @RequestParam String text )
    {
        emailService.sendSimpleEmail( to, subject, text );
        return ResponseEntity.ok( "Correo enviado correctamente!" );
    }
    
    
    /**
     * Endpoint to send an email with a backup.
     *
     * @param from
     *         Sender email address
     * @param subject
     *         Email subject
     * @param text
     *         Email body
     * @return Response message
     */
    @GetMapping( "/send-email-backup" )
    public ResponseEntity<String> sendEmailWithBackup( @RequestParam String from, @RequestParam String subject, @RequestParam String text )
    {
        log.info( "Enviando email a {}", from );
        emailService.sendEmailWithBackup( from, subject, text );
        return ResponseEntity.ok( "Correo enviado correctamente con copia!" );
    }
    
}
