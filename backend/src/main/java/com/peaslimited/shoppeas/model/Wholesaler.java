package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wholesaler {
    @Id
    private String UEN;
    private String name;
    private String email;
    private String phone_number;
    private String currency;
    private Double rating;
    private Integer num_ratings;
}
