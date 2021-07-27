package br.com.gustavoakira.store.productservice.command;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
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
	@ExceptionHandler(resultType = IllegalArgumentException.class)
	public void handle(IllegalArgumentException ex) {
		throw ex;
	}
	
	@ExceptionHandler(resultType = Exception.class)
	public void handle(Exception ex) throws Exception {
		throw ex;
	}
	
	@EventHandler
	public void on(ProductCreatedEvent event) {
		ProductLookupEntity entity = new ProductLookupEntity(event.getProductId(),event.getTitle());
		try{
			repository.save(entity);
		}catch(IllegalArgumentException ex) {
			ex.printStackTrace();
		}
	}
	
	@ResetHandler
	public void reset() {
		repository.deleteAll();
	}
}
