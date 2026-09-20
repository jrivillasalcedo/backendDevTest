package com.example.similar_products.application.port.out;

import com.example.similar_products.domain.model.ProductDetail;
import java.util.List;
import java.util.Optional;

public interface ProductApiClientPort {
    List<String> getSimilarProductIds(String productId);
    Optional<ProductDetail> getProductDetail(String productId);
}
