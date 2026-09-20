package com.example.similar_products.infrastructure.adapter.out.client;

import com.example.similar_products.application.port.out.ProductApiClientPort;
import com.example.similar_products.domain.exception.ProductNotFoundException;
import com.example.similar_products.domain.model.ProductDetail;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class ProductRestClientAdapter implements ProductApiClientPort {

    private static final Logger log = LoggerFactory.getLogger(ProductRestClientAdapter.class);
    private final RestClient productRestClient;

    public ProductRestClientAdapter(RestClient productRestClient) {
        this.productRestClient = productRestClient;
    }

    @Override
    public List<String> getSimilarProductIds(String productId) {
        try {
            return productRestClient.get()
                    .uri("/product/{productId}/similarids", productId)
                    .retrieve()
                    .onStatus(status -> status.value() == 404, (req, res) -> {
                        throw new ProductNotFoundException(productId);
                    })
                    .body(new ParameterizedTypeReference<List<String>>() {});
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error fetching similar product ids for productId {}: {}", productId, e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<ProductDetail> getProductDetail(String productId) {
        try {
            ProductDetail detail = productRestClient.get()
                    .uri("/product/{productId}", productId)
                    .retrieve()
                    .body(ProductDetail.class);
            return Optional.ofNullable(detail);
        } catch (Exception e) {
            log.warn("Failed to fetch detail for product {}: {}", productId, e.getMessage());
            return Optional.empty();
        }
    }
}
