package com.example.similar_products.infrastructure.adapter.in.rest;

import com.example.similar_products.application.port.in.GetSimilarProductsUseCase;
import com.example.similar_products.domain.model.ProductDetail;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductController {

    private final GetSimilarProductsUseCase getSimilarProductsUseCase;

    public ProductController(GetSimilarProductsUseCase getSimilarProductsUseCase) {
        this.getSimilarProductsUseCase = getSimilarProductsUseCase;
    }

    @GetMapping("/{productId}/similar")
    public ResponseEntity<List<ProductDetail>> getSimilarProducts(@PathVariable String productId) {
        List<ProductDetail> products = getSimilarProductsUseCase.getSimilarProducts(productId);
        return ResponseEntity.ok(products);
    }
}
