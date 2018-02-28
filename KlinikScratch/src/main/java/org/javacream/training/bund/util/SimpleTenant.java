package org.javacream.training.bund.util;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Data;

@Data
@Component
@RequestScope(proxyMode=ScopedProxyMode.TARGET_CLASS)
public class SimpleTenant {

	private String tenantId;
}
