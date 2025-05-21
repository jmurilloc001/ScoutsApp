package com.jmurilloc.pfc.scouts.services.interfaces;

public interface EmailService
{
    
    void sendSimpleEmail( String to, String subject, String text );
}
