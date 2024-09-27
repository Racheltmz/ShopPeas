package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerAddress {
    @Id
    private String UEN;
    private String street_name;
    private String unit_no;
    private String building_name;
    private String city;
    private Integer postal_code;

    public WholesalerAddress(String street_name, String unit_no, String building_name, String city, Integer postal_code) {
        this.street_name = street_name;
        this.unit_no = unit_no;
        this.building_name = building_name;
        this.city = city;
        this.postal_code = postal_code;
    }

    public String getStreet_name() {
        return street_name;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public String getCity() {
        return city;
    }

    public Integer getPostal_code() {
        return postal_code;
    }
}
