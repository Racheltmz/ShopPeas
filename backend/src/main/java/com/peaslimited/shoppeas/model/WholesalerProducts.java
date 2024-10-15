package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerProducts {
    @Id
    private String swpid;
    private String uen;
    private String pid;
    private float price;
    private float stock;

    public WholesalerProducts(String uen, String pid, float price, float stock) {
        this.uen = uen;
        this.pid = pid;
        this.price = price;
        this.stock = stock;
    }

}