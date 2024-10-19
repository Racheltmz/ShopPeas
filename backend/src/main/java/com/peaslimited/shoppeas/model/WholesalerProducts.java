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
    private double price;
    private Integer stock;
    private Boolean active;
}
