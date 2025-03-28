package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Product;
import com.jmurilloc.pfc.scouts.exceptions.ProductCouldntCreateException;
import com.jmurilloc.pfc.scouts.exceptions.ProductNotFoundException;
import com.jmurilloc.pfc.scouts.repositories.ProductRepository;
import com.jmurilloc.pfc.scouts.util.MessageError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        List<Product> byPriceGreaterThan = productRepository.findByPriceGreaterThan(amount);
        if (byPriceGreaterThan.isEmpty()) throw new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getValue());
        return byPriceGreaterThan;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> productsWithPriceLessThan(float amount) {
        List<Product> byPriceLessThan = productRepository.findByPriceLessThan(amount);
        if (byPriceLessThan.isEmpty()) throw new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getValue());
        return byPriceLessThan;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> productsWithLastPurcharseAfter(Date date) {
        List<Product> byLastpurchaseAfter = productRepository.findByLastpurchaseAfter(date);
        if (byLastpurchaseAfter.isEmpty()) throw new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getValue());
        return byLastpurchaseAfter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> productsWithLastPurcharseBefore(Date date) {
        List<Product> byLastpurchaseBefore = productRepository.findByLastpurchaseBefore(date);
        if (byLastpurchaseBefore.isEmpty()) throw new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getValue());
        return byLastpurchaseBefore;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> listAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        if (allProducts.isEmpty()) throw new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getValue());
        return allProducts;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Product> listAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Product saveProduct(Product product) {
        Product saveProduct = productRepository.save(product);
        if (saveProduct.getId() == null || saveProduct.getId() == 0) throw new ProductCouldntCreateException(MessageError.CREATE_ERROR.getValue());
        return saveProduct;
    }

    @Transactional(readOnly = true)
    @Override
    public Product findById(Long id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) throw new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getValue());
        return byId.orElseThrow();
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) throw new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getValue());
        productRepository.deleteById(id);
    }
}
