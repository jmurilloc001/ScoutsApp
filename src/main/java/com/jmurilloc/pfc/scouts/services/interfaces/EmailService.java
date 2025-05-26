package com.jmurilloc.pfc.scouts.services.interfaces;

public interface EmailService
{
    
    void sendSimpleEmail( String to, String subject, String text );
    
    
    void sendEmailWithBackup( String from, String subject, String text );
    
}
