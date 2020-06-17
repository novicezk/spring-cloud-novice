package com.novice.framework.cloud.loadbalancer;

import com.novice.framework.cloud.commons.support.StringHelper;
import com.novice.framework.cloud.loadbalancer.chooser.ChooserType;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.MethodMetadata;

public class ChooserCondition extends SpringBootCondition implements ConfigurationCondition {

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		if (!(metadata instanceof MethodMetadata)) {
			return ConditionOutcome.noMatch("must conditional on method");
		}
		String methodName = ((MethodMetadata) metadata).getMethodName();
		Environment environment = context.getEnvironment();
		BindResult<ChooserType> specified = Binder.get(environment).bind("spring.cloud.novice.loadbalancer.chooser-type", ChooserType.class);
		ChooserType chooserType = specified.orElse(ChooserType.DEFAULT);
		String chooserName = StringHelper.underscoreToCamelCase(chooserType.name().toLowerCase());
		if (methodName.startsWith(chooserName)) {
			return ConditionOutcome.match();
		}
		return ConditionOutcome.noMatch("");
	}

	@Override
	public ConfigurationPhase getConfigurationPhase() {
		return ConfigurationPhase.REGISTER_BEAN;
	}
}
