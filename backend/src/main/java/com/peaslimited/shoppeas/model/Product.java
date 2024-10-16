package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String pid; // store the firebase document id
    private String name;
    private String package_size;
    private String image_url;

    // used for creating a new product without assigning a PID
    public Product(String name, String package_size, String image_url){
        this.name = name;
        this.package_size = package_size;
        this.image_url = image_url;
    }
}
