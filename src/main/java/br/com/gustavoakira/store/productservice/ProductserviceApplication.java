package br.com.gustavoakira.store.productservice;

import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

import br.com.gustavoakira.store.productservice.command.interceptor.CreateProductCommandInteceptor;

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
}
