package com.paulomarchon.reactivedorotechtest.service;

import com.paulomarchon.reactivedorotechtest.dto.ProductDto;
import com.paulomarchon.reactivedorotechtest.dto.ProductRegistrationRequest;
import com.paulomarchon.reactivedorotechtest.dto.ProductUpdateRequest;
import com.paulomarchon.reactivedorotechtest.exception.ProductAlreadyRegisteredException;
import com.paulomarchon.reactivedorotechtest.exception.ProductNotFoundException;
import com.paulomarchon.reactivedorotechtest.mapper.ProductDtoMapper;
import com.paulomarchon.reactivedorotechtest.model.Product;
import com.paulomarchon.reactivedorotechtest.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;

    public ProductService(ProductRepository productRepository, ProductDtoMapper productDtoMapper) {
        this.productRepository = productRepository;
        this.productDtoMapper = productDtoMapper;
    }

    public Flux<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .map(productDtoMapper);
    }

    public Mono<ProductDto> getProduct(String id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product with specified ID was not found")))
                .map(productDtoMapper);
    }

    public Mono<ProductDto> registerProduct(ProductRegistrationRequest productRegistrationRequest) {
        return productRepository.existsByName(productRegistrationRequest.name())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new ProductAlreadyRegisteredException("Product with name provided already has registration"));
                    } else {
                        Product product = new Product(
                                productRegistrationRequest.name(),
                                productRegistrationRequest.description(),
                                productRegistrationRequest.price(),
                                productRegistrationRequest.amount()
                        );
                        return productRepository.save(product)
                                .map(productDtoMapper);
                    }
                });
    }

    public Mono<ProductDto> updateProduct(String id, ProductUpdateRequest productUpdateRequest) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product with specified ID was not found")))
                .flatMap(product -> {
                    product.setName(productUpdateRequest.name());
                    product.setDescription(productUpdateRequest.description());
                    product.setPrice(productUpdateRequest.price());
                    product.setAmount(productUpdateRequest.amount());
                    return productRepository.save(product);
                })
                .map(productDtoMapper);
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product with specified ID was not found")))
                .flatMap(product -> productRepository.deleteById(id));
    }
}
