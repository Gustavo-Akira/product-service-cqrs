package br.com.gustavoakira.store.productservice.query;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.com.gustavoakira.store.productservice.core.data.ProductEntity;
import br.com.gustavoakira.store.productservice.core.data.ProductRepository;
import br.com.gustavoakira.store.productservice.query.rest.ProductRestModel;

@Service
public class ProductsQueryHandler {
	
	private final ProductRepository productRepository;
	
	public ProductsQueryHandler(ProductRepository repository) {
		this.productRepository = repository;
	}
	
	@QueryHandler
	public List<ProductRestModel> findProducts(){
		List<ProductRestModel> productsRest = new ArrayList<>();
		List<ProductEntity> products = productRepository.findAll();
		productsRest = products.stream().map(x -> {
			ProductRestModel model = new ProductRestModel();
			BeanUtils.copyProperties(x, model);
			return model;
		}).toList();
		return productsRest;
	}
}
