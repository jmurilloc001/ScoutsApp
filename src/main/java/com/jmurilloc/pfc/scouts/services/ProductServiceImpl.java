package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Product;
import com.jmurilloc.pfc.scouts.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{


    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> productsWithPriceGreaterThan(float amount) {
        return productRepository.findByPriceGreaterThan(amount);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> productsWithPriceLessThan(float amount) {
        return productRepository.findByPriceLessThan(amount);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> productsWithLastPurcharseAfter(Date date) {
        return productRepository.findByLastpurchaseAfter(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> productsWithLastPurcharseBefore(Date date) {
        return productRepository.findByLastpurchaseBefore(date);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    @Override
    public Product saveProduct(Product product) {
       return productRepository.save(product);
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
}
