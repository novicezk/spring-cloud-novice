package com.novice.framework.cloud.loadbalancer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties("spring.cloud.novice.loadbalancer")
public class NoviceLoadbalancerProperties {

}
