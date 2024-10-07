package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerAddressDTO {
    private String street_name;
    private String unit_no;
    private String building_name;
    private String city;
    private Integer postal_code;
}
