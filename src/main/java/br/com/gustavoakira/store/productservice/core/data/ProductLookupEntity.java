package br.com.gustavoakira.store.productservice.core.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name="productlookup")
@AllArgsConstructor
public class ProductLookupEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String productId;
	
	@Column(unique=true)
	private String title;
}
