package br.com.gustavoakira.store.productservice;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import br.com.gustavoakira.store.productservice.command.interceptor.CreateProductCommandInteceptor;
import br.com.gustavoakira.store.productservice.core.errorhandling.ProductServiceEventErrorHandler;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductserviceApplication.class, args);
	}
	
	@Autowired
	public void registerCreateProductCommandIntecepetor(ApplicationContext applicationContext, CommandBus bus) {
		bus.registerDispatchInterceptor(applicationContext.getBean(CreateProductCommandInteceptor.class));
	}
	
	@Autowired
	public void configure(EventProcessingConfigurer configurer) {
		configurer.registerListenerInvocationErrorHandler("product-group",conf -> new ProductServiceEventErrorHandler());
	}
	
	@Bean(name="productSnapshotTrigger")
	public SnapshotTriggerDefinition productSnapshotTriggerDefinition(Snapshotter snapshotter) {
		return new EventCountSnapshotTriggerDefinition(snapshotter, 3);
	}
}
