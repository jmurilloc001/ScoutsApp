package com.jmurilloc.pfc.scouts.repositories;

import com.jmurilloc.pfc.scouts.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    List<Product> findByPriceGreaterThan(float amount);
    List<Product> findByPriceLessThan(float amount);
    List<Product> findByLastpurchaseAfter(Date date);
    List<Product> findByLastpurchaseBefore(Date date);
    boolean existsByName(String name);

}
