package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.annotation.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerAddress {
    @Id
    private String UID;
    private String street_name;
    @Nullable
    private String unit_no;
    @Nullable
    private String building_name;
    private String city;
    private String postal_code;

}
