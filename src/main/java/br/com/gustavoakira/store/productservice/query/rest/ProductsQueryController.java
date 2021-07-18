package br.com.gustavoakira.store.productservice.query.rest;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavoakira.store.productservice.query.FindProductsQuery;

@RestController
@RequestMapping("/products")
public class ProductsQueryController {
	
	@Autowired
	QueryGateway gateway;
	
	@GetMapping
	public List<ProductRestModel> getProducts(){
		FindProductsQuery findProductsQuery = new FindProductsQuery();
		List<ProductRestModel> productRestModels = gateway.query(findProductsQuery, ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();
		return productRestModels;
	}
}
