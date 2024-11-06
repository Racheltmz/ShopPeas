package com.peaslimited.shoppeas.factory;

import com.peaslimited.shoppeas.strategy.RegistrationStrategy;
import com.peaslimited.shoppeas.strategy.impl.ConsumerRegistrationStrategy;
import com.peaslimited.shoppeas.strategy.impl.WholesalerRegistrationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory class that provides the appropriate registration strategy based on the user type.
 * It determines whether the registration process should be handled by the `ConsumerRegistrationStrategy`
 * or the `WholesalerRegistrationStrategy` based on the input provided.
 */
@Component
public class RegistrationFactory {

    /**
     * The strategy used for consumer registration.
     */
    @Autowired
    private ConsumerRegistrationStrategy consumerRegistrationStrategy;

    /**
     * The strategy used for wholesaler registration.
     */
    @Autowired
    private WholesalerRegistrationStrategy wholesalerRegistrationStrategy;

    /**
     * Retrieves the appropriate registration strategy based on the provided user type.
     *
     * @param userType A string indicating the type of user (e.g., "consumer" or "wholesaler").
     * @return A {@link RegistrationStrategy} instance corresponding to the specified user type.
     * @throws IllegalArgumentException if the provided user type is not recognized.
     */
    public RegistrationStrategy getRegistrationStrategy(String userType) {
        return switch (userType.toLowerCase()) {
            case "consumer" -> consumerRegistrationStrategy;
            case "wholesaler" -> wholesalerRegistrationStrategy;
            default -> throw new IllegalArgumentException("Unknown user type: " + userType);
        };
    }
}
