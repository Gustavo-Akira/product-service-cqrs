package br.com.gustavoakira.store.productservice.core.errorhandling;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {
	private final String message;
	private final Date timestamp;
}
