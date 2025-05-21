package com.jmurilloc.pfc.scouts.exceptions;

import org.springframework.mail.MailException;

public class EmailError extends MailException
{
    
    public EmailError( String message )
    {
        super( message );
    }
}
