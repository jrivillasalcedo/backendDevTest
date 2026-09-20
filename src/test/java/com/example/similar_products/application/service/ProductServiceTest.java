package com.example.similar_products.application.service;

import com.example.similar_products.application.port.out.ProductApiClientPort;
import com.example.similar_products.domain.exception.ProductNotFoundException;
import com.example.similar_products.domain.model.ProductDetail;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.task.SyncTaskExecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductApiClientPort productApiClientPort;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productApiClientPort, new SyncTaskExecutor());
    }

    @Test
    void getSimilarProducts_shouldReturnOrderedProductDetails() {
        String productId = "1";
        List<String> similarIds = List.of("2", "3", "4");

        ProductDetail p2 = new ProductDetail("2", "Dress", BigDecimal.valueOf(19.99), true);
        ProductDetail p3 = new ProductDetail("3", "Blazer", BigDecimal.valueOf(29.99), false);
        ProductDetail p4 = new ProductDetail("4", "Boots", BigDecimal.valueOf(39.99), true);

        when(productApiClientPort.getSimilarProductIds(productId)).thenReturn(similarIds);
        when(productApiClientPort.getProductDetail("2")).thenReturn(Optional.of(p2));
        when(productApiClientPort.getProductDetail("3")).thenReturn(Optional.of(p3));
        when(productApiClientPort.getProductDetail("4")).thenReturn(Optional.of(p4));

        List<ProductDetail> result = productService.getSimilarProducts(productId);

        assertEquals(3, result.size());
        assertEquals(List.of(p2, p3, p4), result);
    }

    @Test
    void getSimilarProducts_whenOneDetailFails_shouldReturnRemainingDetails() {
        String productId = "1";
        List<String> similarIds = List.of("2", "5", "4");

        ProductDetail p2 = new ProductDetail("2", "Dress", BigDecimal.valueOf(19.99), true);
        ProductDetail p4 = new ProductDetail("4", "Boots", BigDecimal.valueOf(39.99), true);

        when(productApiClientPort.getSimilarProductIds(productId)).thenReturn(similarIds);
        when(productApiClientPort.getProductDetail("2")).thenReturn(Optional.of(p2));
        when(productApiClientPort.getProductDetail("5")).thenReturn(Optional.empty());
        when(productApiClientPort.getProductDetail("4")).thenReturn(Optional.of(p4));

        List<ProductDetail> result = productService.getSimilarProducts(productId);

        assertEquals(2, result.size());
        assertEquals(List.of(p2, p4), result);
    }

    @Test
    void getSimilarProducts_whenProductNotFound_shouldPropagateException() {
        String productId = "999";

        when(productApiClientPort.getSimilarProductIds(productId)).thenThrow(new ProductNotFoundException(productId));

        assertThrows(ProductNotFoundException.class, () -> productService.getSimilarProducts(productId));
    }
}
