package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Product;
import com.jmurilloc.pfc.scouts.services.ProductService;
import com.jmurilloc.pfc.scouts.util.BuildDate;
import com.jmurilloc.pfc.scouts.util.MessageError;
import com.jmurilloc.pfc.scouts.util.UtilValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = {"http://localhost:5173"})
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
        return service.productsWithPriceGreaterThan(priceMin);
    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @GetMapping(params = "price-max")
    public List<Product> productsWhosePriceLessThan(@RequestParam(name = "price-max") Float priceMax) {
        return service.productsWithPriceLessThan(priceMax);
    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @GetMapping(params = "date-after-year")
    public List<Product> productsWhoseDateAfter(@RequestParam(name = "date-after-year") int year){
        Date date = BuildDate.dateByYear(year); //Construyo la fecha
        return service.productsWithLastPurcharseAfter(date);
    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @GetMapping(params = {"date-after-year","date-after-month"})
    public List<Product> productsWhoseDateAfter(@RequestParam(name = "date-after-year") int year,@RequestParam(name = "date-after-month") int month){
        Date date = BuildDate.dateByYearAndMonth(year,month); //Construyo la fecha
        return service.productsWithLastPurcharseAfter(date);
    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @GetMapping(params = {"date-after-year","date-after-month","date-after-day"})
    public List<Product> productsWhoseDateAfter(@RequestParam(name = "date-after-year") int year,@RequestParam(name = "date-after-month") int month, @RequestParam(name = "date-after-day") int day){
        Date date = BuildDate.dateByYearAndMonthAndDay(year,month,day); //Construyo la fecha
        return service.productsWithLastPurcharseAfter(date);

    }

    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @GetMapping("/date-after")
    public List<Product> productsWhoseDateAfter(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){ //localhost:8080/products/date-after?date=2010-01-01
        return service.productsWithLastPurcharseAfter(date);
    }

    /**
     * Retrieves a list of products whose last purchase date is before the specified date.
     *
     * @param date the date to compare
     * @return a list of products with last purchase date before the specified date
     */
    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @GetMapping("/date-before")
    public List<Product> productsWhoseDateBefore(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){ //localhost:8080/products/date-after?date=2010-01-01
        return service.productsWithLastPurcharseBefore(date);
    }

    /**
     * Retrieves a list of all products.
     *
     * @return a list of all products
     */
    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @GetMapping
    public List<Product> index(){
        return service.listAllProducts();
    }
    /**
     * Retrieves a paginated list of products.
     *
     * @param page the page number to retrieve
     * @param size the size of the page to retrieve
     * @return a paginated list of products
     */
    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @GetMapping("/page")
    public Page<Product> indexPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.listAllProducts(pageable);
    }

    /**
     * Saves a new product.
     *
     * @param product the product to save
     * @param result the binding result
     * @return a response entity with the saved product
     */
    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody Product product,BindingResult result){
        if (result.hasFieldErrors()){
            return UtilValidation.validation(result);
        }
        Product p = service.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    /**
     * Updates an existing product.
     *
     * @param product the product to update
     * @param result the binding result
     * @param id the id of the product to update
     * @return a response entity with the updated product
     */
    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody Product product,BindingResult result,@PathVariable Long id){

        BindingResult newResult = UtilValidation.validateWithoutError(MessageError.VALIDATE_EXISTS_PRODUCT_BY_NAME.getValue(), result);
        if (newResult != null){
            return UtilValidation.validation(newResult);
        }

        service.findById(id); //Para hacer la comprobación de que existe el producto

        product.setId(id);
        service.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    /**
     * Updates some fields of an existing product.
     *
     * @param product the product to update
     * @param result the binding result
     * @param id the id of the product to update
     * @return a response entity with the updated product
     */
    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateSomeFields(@Valid @RequestBody Product product, BindingResult result, @PathVariable Long id){

        BindingResult newResult = UtilValidation.validateWithoutError(MessageError.VALIDATE_EXISTS_PRODUCT_BY_NAME.getValue(), result);
        if (newResult != null){
            return UtilValidation.validation(newResult);
        }

        Product p = service.findById(id);

        p.setName(product.getName());
        p.setPrice(product.getPrice());
        p.setStock(product.getStock());

        if (product.getLastpurchase() != null) {p.setLastpurchase(product.getLastpurchase());}

        service.saveProduct(p);

        return ResponseEntity.ok(p);
    }

    /**
     * Deletes an existing product.
     *
     * @param id the id of the product to delete
     * @return a response entity with a success message
     */
    @PreAuthorize("hasAnyRole('ADMIN','COORDI','SCOUTER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Product product = service.findById(id);
        service.deleteProduct(product.getId());
        return ResponseEntity.ok("Producto eliminado con éxito");
    }
}
