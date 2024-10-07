package com.peaslimited.shoppeas.dto.mapper;

import com.peaslimited.shoppeas.dto.ConsumerAccountDTO;
import com.peaslimited.shoppeas.dto.ConsumerAddressDTO;
import com.peaslimited.shoppeas.dto.ConsumerDTO;
import com.peaslimited.shoppeas.dto.ConsumerProfileDTO;

public class ConsumerProfileMapper {

    public static ConsumerProfileDTO toProfileDTO(ConsumerDTO consumer,
                                                    ConsumerAddressDTO consumerAddress,
                                                    ConsumerAccountDTO consumerAccount) {
        return new ConsumerProfileDTO(consumer, consumerAccount, consumerAddress);
    }
}
