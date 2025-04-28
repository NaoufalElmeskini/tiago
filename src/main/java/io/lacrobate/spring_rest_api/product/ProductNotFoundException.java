package io.lacrobate.spring_rest_api.product;

public class ProductNotFoundException extends RuntimeException {
	public ProductNotFoundException(String priceNotFound) {
		super(priceNotFound);
	}
}
