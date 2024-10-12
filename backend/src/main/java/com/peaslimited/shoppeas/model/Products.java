package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    @Id
    String pid;
    String image_url;
    String name;
    String package_size;
}
