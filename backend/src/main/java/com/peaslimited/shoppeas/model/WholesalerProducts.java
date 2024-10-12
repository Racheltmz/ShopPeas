package com.peaslimited.shoppeas.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerProducts {

    @Id
    String swp_id;
    String pid;
    double price;
    int stock;
    String uen;

}
