package com.paulomarchon.reactivedorotechtest.repository;

import com.paulomarchon.reactivedorotechtest.model.Product;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<Product, String> {
    @AllowFiltering
    Mono<Boolean> existsByName(String name);
}
