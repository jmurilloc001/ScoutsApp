package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Product;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> productsWithPriceGreaterThan(float amount);
    List<Product> productsWithPriceLessThan(float amount);
    List<Product> productsWithLastPurcharseAfter(Date date);
    List<Product> listAllProducts();
    Product saveProduct(Product product);
    void deleteProduct(Long id);
    Optional<Product> findById(Long id);
    Product update(Product product);
    boolean existsByName(String name);
}
