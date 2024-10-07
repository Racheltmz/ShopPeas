package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerAccount {
    @Id
    private String UID;
    private Long card_no;
    private String expiry_date;
    private Integer cvv;
    private String name;

}
