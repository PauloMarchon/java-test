package com.paulomarchon.reactivedorotechtest.mapper;

import com.paulomarchon.reactivedorotechtest.dto.ProductDto;
import com.paulomarchon.reactivedorotechtest.model.Product;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductDtoMapper implements Function<Product, ProductDto> {
    @Override
    public ProductDto apply(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAmount()
        );
    }
}
