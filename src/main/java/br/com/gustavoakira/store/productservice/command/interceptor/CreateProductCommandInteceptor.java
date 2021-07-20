package br.com.gustavoakira.store.productservice.command.interceptor;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.gustavoakira.store.productservice.command.CreateProductCommand;
import br.com.gustavoakira.store.productservice.core.data.ProductLookupEntity;
import br.com.gustavoakira.store.productservice.core.data.ProductLookupRepository;

@Component
public class CreateProductCommandInteceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInteceptor.class);
	
	private final ProductLookupRepository repository;
	
	@Autowired
	public CreateProductCommandInteceptor(ProductLookupRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
			List<? extends CommandMessage<?>> messages) {
		
		return (index,command)->{
			LOGGER.info("Intecepted command "+command.getPayloadType());
			if(CreateProductCommand.class.equals(command.getPayloadType())) {
				CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();
				ProductLookupEntity entity = repository.findByProductIdOrTitle(createProductCommand.getProductId(), createProductCommand.getTitle());
				if(entity != null){
					throw new IllegalStateException(String.format("Product with productId %s or title %s already exist", createProductCommand.getProductId(),createProductCommand.getTitle()));
				}
			}
			return command;
		};
	}

}
