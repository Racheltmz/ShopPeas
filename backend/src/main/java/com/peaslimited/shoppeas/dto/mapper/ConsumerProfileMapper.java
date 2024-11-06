package com.peaslimited.shoppeas.dto.mapper;

import com.peaslimited.shoppeas.dto.ConsumerAddressDTO;
import com.peaslimited.shoppeas.dto.ConsumerDTO;
import com.peaslimited.shoppeas.dto.ConsumerProfileDTO;

/**
 * Utility class responsible for mapping data between different DTOs.
 * Specifically, this class is used to map a `ConsumerDTO` and `ConsumerAddressDTO`
 * to a `ConsumerProfileDTO`, which represents a complete consumer profile.
 */
public class ConsumerProfileMapper {

    /**
     * Maps a {@link ConsumerDTO} and a {@link ConsumerAddressDTO} to a {@link ConsumerProfileDTO}.
     * @param consumer The `ConsumerDTO` containing the consumer's basic profile information.
     * @param consumerAddress The `ConsumerAddressDTO` containing the consumer's address details.
     * @return A `ConsumerProfileDTO` containing both the consumer's information and address.
     */
    public static ConsumerProfileDTO toProfileDTO(ConsumerDTO consumer, ConsumerAddressDTO consumerAddress) {
        return new ConsumerProfileDTO(consumer, consumerAddress);
    }
}
