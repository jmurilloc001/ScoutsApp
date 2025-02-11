package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Product;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


public interface ProductService {

    List<Product> productsWithPriceGreaterThan(float amount);
    List<Product> productsWithPriceLessThan(float amount);
    List<Product> productsWithLastPurcharseAfter(Date date);
    List<Product> listAllProducts();
}
