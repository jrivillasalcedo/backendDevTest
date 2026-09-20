package com.example.similar_products.application.port.in;

import com.example.similar_products.domain.model.ProductDetail;
import java.util.List;

public interface GetSimilarProductsUseCase {
    List<ProductDetail> getSimilarProducts(String productId);
}
