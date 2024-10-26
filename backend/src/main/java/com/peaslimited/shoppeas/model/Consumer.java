package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consumer {
    @Id
    private String UID;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;

}
