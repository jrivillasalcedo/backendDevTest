package com.example.similar_products.application.service;

import com.example.similar_products.application.port.in.GetSimilarProductsUseCase;
import com.example.similar_products.application.port.out.ProductApiClientPort;
import com.example.similar_products.domain.model.ProductDetail;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements GetSimilarProductsUseCase {

    private final ProductApiClientPort productApiClientPort;
    private final Executor productTaskExecutor;

    public ProductService(ProductApiClientPort productApiClientPort,
                          @Qualifier("productTaskExecutor") Executor productTaskExecutor) {
        this.productApiClientPort = productApiClientPort;
        this.productTaskExecutor = productTaskExecutor;
    }

    @Override
    public List<ProductDetail> getSimilarProducts(String productId) {
        List<String> similarIds = productApiClientPort.getSimilarProductIds(productId);
        if (similarIds == null || similarIds.isEmpty()) {
            return List.of();
        }

        List<CompletableFuture<Optional<ProductDetail>>> futures = similarIds.stream()
                .map(id -> CompletableFuture.supplyAsync(() -> productApiClientPort.getProductDetail(id), productTaskExecutor)
                        .exceptionally(ex -> Optional.empty()))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .flatMap(Optional::stream)
                .filter(Objects::nonNull)
                .toList();
    }
}
