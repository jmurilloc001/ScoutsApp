package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.services.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
    public String sendEmail( @RequestParam String to, @RequestParam String subject, @RequestParam String text )
    {
        emailService.sendSimpleEmail( to, subject, text );
        return "Correo enviado correctamente!";
    }
    
}
