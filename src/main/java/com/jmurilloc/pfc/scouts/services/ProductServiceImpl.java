package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Product;
import com.jmurilloc.pfc.scouts.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> productsWithPriceGreaterThan(float amount) {
        return productRepository.findByPriceGreaterThan(amount);
    }

    @Override
    public List<Product> productsWithPriceLessThan(float amount) {
        return productRepository.findByPriceLessThan(amount);
    }

    @Override
    public List<Product> productsWithLastPurcharseAfter(Date date) {
        return productRepository.findByLastpurchaseAfter(date);
    }

    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }
}
