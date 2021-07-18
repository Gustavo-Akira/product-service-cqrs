package br.com.gustavoakira.store.productservice.command.rest;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavoakira.store.productservice.command.CreateProductCommand;

@RestController
@RequestMapping("/products")
public class ProductsCommandController {
	
	@Autowired
	private CommandGateway commandGateway ;
	
	
	
	@GetMapping
	public String getProduct() {
		return "GET";
	}
	
	@PostMapping
	public String createProduct(@RequestBody CreateProductRestModel createProductRestModel) {
		CreateProductCommand createProductCommand = CreateProductCommand.builder()
				.price(createProductRestModel.getPrice())
				.quantity(createProductRestModel.getQuantity())
				.title(createProductRestModel.getTitle())
				.productId(UUID.randomUUID().toString()).build();
		String returnValue;
		try {
			returnValue = commandGateway.sendAndWait(createProductCommand);
		}catch(Exception e) {
			returnValue = e.getLocalizedMessage();
		}
		return returnValue;
	}
}
