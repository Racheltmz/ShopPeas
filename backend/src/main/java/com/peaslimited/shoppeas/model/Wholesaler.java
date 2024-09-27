package com.peaslimited.shoppeas.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Wholesaler {
    @Id
    private String UEN;
    private String name;
    private String email;
    private String phone_number;
    private String currency;
    private Double rating;
    private Integer num_ratings;

    public Wholesaler(String UEN, String name, String email, String phone_number, String currency, Double rating, Integer num_ratings) {
        this.UEN = UEN;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.currency = currency;
        this.rating = rating;
        this.num_ratings = num_ratings;
    }

    public String getUEN() {
        return UEN;
    }
}
