package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Product;
import com.jmurilloc.pfc.scouts.exceptions.ProductCouldntCreateException;
import com.jmurilloc.pfc.scouts.exceptions.ProductNotFoundException;
import com.jmurilloc.pfc.scouts.services.ProductService;
import com.jmurilloc.pfc.scouts.util.BuildDate;
import com.jmurilloc.pfc.scouts.util.MessageError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/products")
public class ProductController {


    private ProductService service;

    @Autowired
    public ProductController(ProductService service){
        this.service = service;
    }

    @GetMapping(params = "price-min")
    public List<Product> productsWhosePriceGreaterThan(@RequestParam(name = "price-min") Float priceMin) {
        List<Product> productos = service.productsWithPriceGreaterThan(priceMin);
        checkEmptyListOfProducts(productos);

        return productos;

    }

    @GetMapping(params = "price-max")
    public List<Product> productsWhosePriceLessThan(@RequestParam(name = "price-max") Float priceMax) {
        List<Product> productos = service.productsWithPriceLessThan(priceMax);
        checkEmptyListOfProducts(productos);
        return productos;
    }

    @GetMapping(params = "date-after-year")
    public List<Product> productsWhoseDateAfter(@RequestParam(name = "date-after-year") int year){
        Date date = BuildDate.dateByYear(year); //Construyo la fecha
        List<Product> products = service.productsWithLastPurcharseAfter(date);
        checkEmptyListOfProducts(products);
        return products;
    }

    @GetMapping(params = {"date-after-year","date-after-month"})
    public List<Product> productsWhoseDateAfter(@RequestParam(name = "date-after-year") int year,@RequestParam(name = "date-after-month") int month){
        Date date = BuildDate.dateByYearAndMonth(year,month); //Construyo la fecha
        List<Product> products = service.productsWithLastPurcharseAfter(date);
        checkEmptyListOfProducts(products);
        return products;
    }

    @GetMapping(params = {"date-after-year","date-after-month","date-after-day"})
    public List<Product> productsWhoseDateAfter(@RequestParam(name = "date-after-year") int year,@RequestParam(name = "date-after-month") int month, @RequestParam(name = "date-after-day") int day){
        Date date = BuildDate.dateByYearAndMonthAndDay(year,month,day); //Construyo la fecha
        List<Product> products = service.productsWithLastPurcharseAfter(date);
        checkEmptyListOfProducts(products);
        return products;
    }

    //TODO Me falta hacer lo mismo, pero para BEFORE, pero quiero esperar a como me da la fecha el front

    @GetMapping
    public List<Product> index(){
        List<Product> products = service.listAllProducts();
        checkEmptyListOfProducts(products);
        return products;
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product){
        Product p = service.saveProduct(product);
        if (p == null){
            throw new ProductCouldntCreateException(MessageError.CREATE_ERROR.getValue());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }
    @PostMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id,@RequestBody Product product){
        product.setId(id);
        Optional<Product> productOptional = service.findById(id);
        if (productOptional.isPresent()){
            Product p = service.update(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(p);
        }
        throw new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getValue());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<Product> product = service.findById(id);
        product.ifPresentOrElse(product1 -> service.deleteProduct(product1.getId()),() -> {throw new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getValue());});

        return ResponseEntity.status(HttpStatus.CREATED).body("Producto eliminado con exito");
    }


    private Map<String,Object> createJsonMap(List<Product> products){
        Map<String,Object> json = new HashMap<>();
        for (Product producto : products) {
            json.put("Producto " + producto.getId(), producto);
        }
        return json;
    }

    private void checkEmptyListOfProducts(List<Product> products){
        if (products.isEmpty()){
            throw new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getValue());
        }
    }

}
