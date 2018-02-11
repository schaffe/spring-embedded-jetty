package com.dzidzoiev;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.annotation.*;

import javax.ws.rs.ext.RuntimeDelegate;
import java.util.Collections;

@Configuration
@Import(EmbeddedServerConfiguration.class)
@ComponentScan
public class AppConfig {

	@Bean( destroyMethod = "shutdown" )
	public SpringBus cxf() {
		return new SpringBus();
	}
	
	@Bean
	@DependsOn ( "cxf" )
	public Server jaxRsServer(MyRestController controller) {
		JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint( jaxRsApiApplication(), JAXRSServerFactoryBean.class );
		factory.setServiceBeans(Collections.singletonList(controller));
		factory.setAddress( factory.getAddress() );
		return factory.create();
	}
	
	@Bean 
	public JaxRsApiApplication jaxRsApiApplication() {
		return new JaxRsApiApplication();
	}
}