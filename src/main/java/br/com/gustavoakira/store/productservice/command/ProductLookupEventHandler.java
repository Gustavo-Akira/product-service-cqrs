package br.com.gustavoakira.store.productservice.command;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.gustavoakira.store.productservice.core.data.ProductLookupEntity;
import br.com.gustavoakira.store.productservice.core.data.ProductLookupRepository;
import br.com.gustavoakira.store.productservice.core.event.ProductCreatedEvent;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventHandler {
	
	private final ProductLookupRepository repository;
	
	@Autowired
	public ProductLookupEventHandler(ProductLookupRepository repository) {
		this.repository = repository;
	}
	
	@EventHandler
	public void on(ProductCreatedEvent event) {
		ProductLookupEntity entity = new ProductLookupEntity(event.getProductId(),event.getTitle());
		repository.save(entity);
	}
}
