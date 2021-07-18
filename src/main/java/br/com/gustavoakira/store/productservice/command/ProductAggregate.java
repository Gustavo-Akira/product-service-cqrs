package br.com.gustavoakira.store.productservice.command;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import br.com.gustavoakira.store.productservice.core.event.ProductCreatedEvent;

@Aggregate
public class ProductAggregate {
	
	@AggregateIdentifier
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;
	
	public ProductAggregate() {
	}
	
	@CommandHandler
	public ProductAggregate(CreateProductCommand command) {
		if(command.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Price cannot be less or equal than zero");
		}
		
		if(command.getTitle() == null || command.getTitle().isBlank()) {
			throw new IllegalArgumentException("Title cannot be empty");
		}
		
		ProductCreatedEvent createdEvent = new ProductCreatedEvent();
		BeanUtils.copyProperties(command, createdEvent);
		AggregateLifecycle.apply(createdEvent);
	}
	
	@EventSourcingHandler
	public void on(ProductCreatedEvent createdEvent) {
		this.productId = createdEvent.getProductId();
		this.price = createdEvent.getPrice();
		this.quantity = createdEvent.getQuantity();
		this.title = createdEvent.getTitle();
	}
}
