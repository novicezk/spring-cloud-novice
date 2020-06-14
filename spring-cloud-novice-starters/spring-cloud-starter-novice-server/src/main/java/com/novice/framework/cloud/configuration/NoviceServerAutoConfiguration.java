package com.novice.framework.cloud.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
@ComponentScan("com.novice.framework.cloud.server")
public class NoviceServerAutoConfiguration {

}
