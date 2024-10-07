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

    public Consumer(String first_name, String last_name, String email, String phone_number) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
    }

}
