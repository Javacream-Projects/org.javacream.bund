package org.javacream.training.bund.pipeline;

import java.util.Date;

import org.javacream.training.bund.dataaccess.SimpleData;
import org.javacream.training.bund.dataaccess.SimpleRepository;
import org.javacream.training.bund.util.SimpleTenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
public class SimplePipeline {

	@Autowired private SimpleRepository simpleRepository;
	@Autowired private SimpleTenant simpleTenant;
	public void enter() {
		System.out.println("entering pipeline");
		SimpleData data = new SimpleData();
		data.setMessage(simpleTenant.getTenantId() + " at " + new Date());
		simpleRepository.save(data);
	}
}
