package com.novice.framework.cloud.loadbalancer;

import com.novice.framework.cloud.loadbalancer.chooser.ChooserType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties("spring.cloud.novice.loadbalancer")
public class NoviceLoadbalancerProperties {
	private ChooserType chooserType = ChooserType.ROUND_ROBIN;
}
