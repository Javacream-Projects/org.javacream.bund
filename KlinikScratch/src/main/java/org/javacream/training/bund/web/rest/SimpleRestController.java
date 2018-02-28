package org.javacream.training.bund.web.rest;

import java.util.Arrays;
import java.util.List;

import org.javacream.training.bund.pipeline.SimplePipeline;
import org.javacream.training.bund.util.SimpleTenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

/**
 * reines demo für Profile! ob das so praktikabel ist-> DISKUSSION
 * @author Sawitzki
 *
 */
@RequestMapping(path = "import")
@RestController
@ApplicationScope
public class SimpleRestController {

	@Autowired
	private Environment environment;
	@Autowired SimpleTenant klinik;
	@Autowired private SimplePipeline simplePipeline;
	@RequestMapping(path = "/${tenants}{tenantId}", method = RequestMethod.GET, produces = "text/plain")
	public String startImport(@PathVariable("tenantId") String tenantId) {
		List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
		String message;
		if (activeProfiles.contains(tenantId)) {
			klinik.setTenantId(tenantId);
			message = "OK, profile for " + tenantId + " is active";
			System.out.println(message);
			simplePipeline.enter();
			
		}else {
			message = "Error, profile for " + tenantId + " is not active";
			System.err.println(message);
			
		}
		return message;
	}

}
