package com.jmurilloc.pfc.scouts.entities;

import com.jmurilloc.pfc.scouts.vaidation.ExistsProductByName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Table(name = "products")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ExistsProductByName
    @NotBlank
    @Size(min = 2,max = 40)
    private String name;

    @Positive
    private float price;
    @Positive
    private int stock;
    private Date lastpurchase;

    public Product() {
    }

    public Product(Long id, String name, float price, int stock, Date lastpurchase) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.lastpurchase = lastpurchase;
    }

    public Product( String name, float price, int stock, Date lastpurchase) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.lastpurchase = lastpurchase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLastpurchase() {
        return lastpurchase;
    }

    public void setLastpurchase(Date lastpurchase) {
        this.lastpurchase = lastpurchase;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", lastpurchase=" + lastpurchase +
                '}';
    }
}
