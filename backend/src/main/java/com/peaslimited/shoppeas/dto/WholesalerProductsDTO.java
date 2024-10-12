package com.peaslimited.shoppeas.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerProductsDTO {

    String pid;
    double price;
    int stock;
    String uen;
}
