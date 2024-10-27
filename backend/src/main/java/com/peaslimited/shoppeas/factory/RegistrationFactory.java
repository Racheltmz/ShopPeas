package com.peaslimited.shoppeas.factory;

import com.peaslimited.shoppeas.strategy.RegistrationStrategy;
import com.peaslimited.shoppeas.strategy.impl.ConsumerRegistrationStrategy;
import com.peaslimited.shoppeas.strategy.impl.WholesalerRegistrationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RegistrationFactory {

    @Autowired
    private ApplicationContext context;

    public RegistrationStrategy getRegistrationStrategy(String userType) {
        return switch (userType.toLowerCase()) {
            case "consumer" -> context.getBean(ConsumerRegistrationStrategy.class);
            case "wholesaler" -> context.getBean(WholesalerRegistrationStrategy.class);
            default -> throw new IllegalArgumentException("Unknown user type: " + userType);
        };
    }
}
