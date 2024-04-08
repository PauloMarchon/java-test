package com.paulomarchon.reactivedorotechtest.controller;

import com.paulomarchon.reactivedorotechtest.dto.ProductDto;
import com.paulomarchon.reactivedorotechtest.dto.ProductRegistrationRequest;
import com.paulomarchon.reactivedorotechtest.dto.ProductUpdateRequest;
import com.paulomarchon.reactivedorotechtest.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "The products api can be used to perform actions on Products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Retrieve products", description = "Use this API to retrieve all registered products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ProductDto.class))),
    })
    @GetMapping
    public ResponseEntity<Flux<ProductDto>> getAllProducts() {
        Flux<ProductDto> productDtoFlux = productService.getAllProducts();
        return new ResponseEntity<>(productDtoFlux, HttpStatus.OK);
    }

    @Operation(summary = "Gets an prouct", description = "Use this API to retrieve a specific Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> getProduct(@PathVariable("id") String id) {
        return productService.getProduct(id)
                .map(productDto -> ResponseEntity.status(HttpStatus.OK).body(productDto))
                .onErrorReturn(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Register a new product", description = "If the product is successfully registered, the response code 201 CREATED will be returned."
           + " in this case, a JSON will be returned with the data of the new registered product along with the response.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PostMapping
    public Mono<ResponseEntity<ProductDto>> registerProduct(@RequestBody @Valid ProductRegistrationRequest registrationRequest) {
        return productService.registerProduct(registrationRequest)
                .map(registeredProduct -> ResponseEntity.status(HttpStatus.CREATED).body(registeredProduct))
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Modifies an product", description = "Use this API to modify a specific Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable("id") String id, @RequestBody @Valid ProductUpdateRequest updateRequest) {
        return productService.updateProduct(id, updateRequest)
                .map(registeredProduct -> ResponseEntity.status(HttpStatus.OK).body(registeredProduct))
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Deletes an product", description = "Use this API to delete a specific Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteProduct(@PathVariable("id") String id) {
        return productService.deleteProduct(id)
                .thenReturn(ResponseEntity.ok().body("Product deleted successfully."))
                .onErrorReturn(ResponseEntity.notFound().build());
    }
}
