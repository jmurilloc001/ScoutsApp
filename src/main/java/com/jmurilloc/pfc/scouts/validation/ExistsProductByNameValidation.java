package com.jmurilloc.pfc.scouts.validation;

import com.jmurilloc.pfc.scouts.services.interfaces.ProductService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistsProductByNameValidation implements ConstraintValidator<ExistsProductByName, String>
{
    
    private ProductService service;
    
    
    @Autowired
    public void setService( ProductService service )
    {
        this.service = service;
    }
    
    
    @Override
    public boolean isValid( String name, ConstraintValidatorContext constraintValidatorContext )
    {
        if( service == null )
        {
            return true;
        }
        return !(service.existsByName( name ));
    }
}
