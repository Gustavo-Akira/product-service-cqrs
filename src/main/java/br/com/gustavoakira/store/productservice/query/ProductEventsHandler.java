package br.com.gustavoakira.store.productservice.query;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.gustavoakira.store.core.events.ProductReservationCancelledEvent;
import br.com.gustavoakira.store.core.events.ProductReservedEvent;
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
	
	@ExceptionHandler(resultType = Exception.class)
	public void handle(Exception e) throws Exception{
		throw e;
	}
	
	
	@ExceptionHandler(resultType = IllegalArgumentException.class)
	public void handle(IllegalArgumentException e) throws Exception{
		
	}
	
	@EventHandler
	public void on(ProductCreatedEvent event) {
		ProductEntity entity = new ProductEntity();
		BeanUtils.copyProperties(event, entity);
		try {
			productRepository.save(entity);
		}catch(IllegalArgumentException argumentException) {
			argumentException.printStackTrace();
		}
	}
	
	@EventHandler
	public void on(ProductReservedEvent productReservedEvent) {
		ProductEntity entity = productRepository.findByProductId(productReservedEvent.getProductId());
		entity.setQuantity(entity.getQuantity() - productReservedEvent.getQuantity());
		productRepository.save(entity);
	}
	
	@EventHandler
	public void on(ProductReservationCancelledEvent cancelledEvent) {
		ProductEntity entity = productRepository.getById(cancelledEvent.getProductId());
		int updatedQuantity = entity.getQuantity() + cancelledEvent.getQuantity();
		entity.setQuantity(updatedQuantity);
		productRepository.save(entity);
	}
	
	@ResetHandler
	public void reset() {
		productRepository.deleteAll();
	}
}
