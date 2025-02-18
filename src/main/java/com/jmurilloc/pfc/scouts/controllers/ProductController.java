package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Product;
import com.jmurilloc.pfc.scouts.exceptions.ProductCouldntCreateException;
import com.jmurilloc.pfc.scouts.exceptions.ProductNotDeleteException;
import com.jmurilloc.pfc.scouts.exceptions.ProductNotFoundException;
import com.jmurilloc.pfc.scouts.services.ProductService;
import com.jmurilloc.pfc.scouts.util.BuildDate;
import com.jmurilloc.pfc.scouts.util.MessageError;
import com.jmurilloc.pfc.scouts.util.UtilValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/products")
public class ProductController {


    private ProductService service;

    @Autowired
    public void setService(ProductService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @GetMapping(params = "price-min")
    public List<Product> productsWhosePriceGreaterThan(@RequestParam(name = "price-min") Float priceMin) {
        List<Product> productos = service.productsWithPriceGreaterThan(priceMin);
        checkEmptyListOfProducts(productos);

        return productos;

    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @GetMapping(params = "price-max")
    public List<Product> productsWhosePriceLessThan(@RequestParam(name = "price-max") Float priceMax) {
        List<Product> productos = service.productsWithPriceLessThan(priceMax);
        checkEmptyListOfProducts(productos);
        return productos;
    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @GetMapping(params = "date-after-year")
    public List<Product> productsWhoseDateAfter(@RequestParam(name = "date-after-year") int year){
        Date date = BuildDate.dateByYear(year); //Construyo la fecha
        List<Product> products = service.productsWithLastPurcharseAfter(date);
        checkEmptyListOfProducts(products);
        return products;
    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @GetMapping(params = {"date-after-year","date-after-month"})
    public List<Product> productsWhoseDateAfter(@RequestParam(name = "date-after-year") int year,@RequestParam(name = "date-after-month") int month){
        Date date = BuildDate.dateByYearAndMonth(year,month); //Construyo la fecha
        List<Product> products = service.productsWithLastPurcharseAfter(date);
        checkEmptyListOfProducts(products);
        return products;
    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @GetMapping(params = {"date-after-year","date-after-month","date-after-day"})
    public List<Product> productsWhoseDateAfter(@RequestParam(name = "date-after-year") int year,@RequestParam(name = "date-after-month") int month, @RequestParam(name = "date-after-day") int day){
        Date date = BuildDate.dateByYearAndMonthAndDay(year,month,day); //Construyo la fecha
        List<Product> products = service.productsWithLastPurcharseAfter(date);
        checkEmptyListOfProducts(products);
        return products;
    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @GetMapping("/date-after")
    public List<Product> productsWhoseDateAfter(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){ //localhost:8080/products/date-after?date=2010-01-01
        List<Product> products = service.productsWithLastPurcharseAfter(date);
        checkEmptyListOfProducts(products);
        return products;
    }

    //TODO Me falta hacer lo mismo, pero para BEFORE, pero quiero esperar a como me da la fecha el front

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @GetMapping
    public List<Product> index(){
        List<Product> products = service.listAllProducts();
        checkEmptyListOfProducts(products);
        return products;
    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody Product product,BindingResult result){
        if (result.hasFieldErrors()){
            return UtilValidation.validation(result);
        }
        Product p = service.saveProduct(product);
        if (p == null){
            throw new ProductCouldntCreateException(MessageError.CREATE_ERROR.getValue());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody Product product,BindingResult result,@PathVariable Long id){

        BindingResult newResult = UtilValidation.validateWithoutError(MessageError.VALIDATE_EXISTS_PRODUCT_BY_NAME.getValue(), result);
        if (newResult != null){
            return UtilValidation.validation(newResult);
        }


        Optional<Product> productOptional = service.findById(id);
        if (productOptional.isPresent()){
            product.setId(id);
            service.saveProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        }
        throw new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getValue());
    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateSomeFields(@Valid @RequestBody Product product, BindingResult result, @PathVariable Long id){

        //TODO PREGUNTAR PARA VER SI SE PUEDE MEJORAR

        BindingResult newResult = UtilValidation.validateWithoutError(MessageError.VALIDATE_EXISTS_PRODUCT_BY_NAME.getValue(), result);
        if (newResult != null){
            return UtilValidation.validation(newResult);
        }

        Optional<Product> optionalProduct = service.findById(id);
        if (optionalProduct.isPresent()){
            Product p = optionalProduct.orElseThrow();

            p.setName(product.getName());
            p.setPrice(product.getPrice());
            p.setStock(product.getStock());
            if (product.getLastpurchase() != null) {p.setLastpurchase(product.getLastpurchase());}

            service.saveProduct(p);

            return ResponseEntity.ok(p);
        }
        throw new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getValue());
    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<Product> product = service.findById(id);
        product.ifPresentOrElse(product1 -> service.deleteProduct(product1.getId()),() -> {throw new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getValue());});

        if (service.findById(id).isPresent()){
            throw new ProductNotDeleteException(MessageError.PRODUCT_NOT_DELETED.getValue());
        }
        return ResponseEntity.ok("Producto eliminado con éxito");
    }

    private void checkEmptyListOfProducts(List<Product> products){
        if (products.isEmpty()){
            throw new ProductNotFoundException(MessageError.PRODUCT_NOT_FOUND.getValue());
        }
    }


}
