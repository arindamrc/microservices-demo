package com.adex.gateway.configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.context.annotation.Bean;

/**
 * Ribbon configuration.
 * 
 * @author arc
 */
public class RibbonConfiguration {

    @Bean
    public IPing ribbonPing(final IClientConfig config) {
        return new PingUrl(false,"/health");
    }

//    @Bean
//    public IRule ribbonRule(final IClientConfig config) {
//        return new AvailabilityFilteringRule();
//    }

}
