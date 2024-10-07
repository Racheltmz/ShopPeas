package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerAccountDTO {
    private Long card_no;
    private String expiry_date;
    private Integer cvv;
    private String name;
}
