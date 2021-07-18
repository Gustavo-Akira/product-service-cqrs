package br.com.gustavoakira.store.productservice.query;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.gustavoakira.store.productservice.core.data.ProductEntity;
import br.com.gustavoakira.store.productservice.core.data.ProductRepository;
import br.com.gustavoakira.store.productservice.core.event.ProductCreatedEvent;

@Component
public class ProductEventsHandler {
	
	private final ProductRepository productRepository;
	
	@Autowired
	public ProductEventsHandler(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@EventHandler
	public void on(ProductCreatedEvent event) {
		ProductEntity entity = new ProductEntity();
		BeanUtils.copyProperties(event, entity);
		
		productRepository.save(entity);
	}
}
