/**
 * 
 */
package com.adex.customerservice.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;

/**
 * An embedded cache configuration. A new cache instance is associated
 * with every instance of the customer service.
 * 
 * @author arc
 *
 */
@Configuration
@EnableCaching
public class CacheConfiguration {
	
	public CacheConfiguration() {
	}
	
	@Bean
	Config cacheConfig() {
	    Config config = new Config();

	    MapConfig mapConfig = new MapConfig();
	    mapConfig.setTimeToLiveSeconds(300);
	    config.getMapConfigs().put("customercache", mapConfig);

	    return config;		
	}

}
