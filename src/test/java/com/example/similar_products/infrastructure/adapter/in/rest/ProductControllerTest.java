package com.example.similar_products.infrastructure.adapter.in.rest;

import com.example.similar_products.application.port.in.GetSimilarProductsUseCase;
import com.example.similar_products.domain.exception.ProductNotFoundException;
import com.example.similar_products.domain.model.ProductDetail;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ProductController.class, GlobalExceptionHandler.class})
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetSimilarProductsUseCase getSimilarProductsUseCase;

    @Test
    void getSimilarProducts_shouldReturn200WithList() throws Exception {
        String productId = "1";
        ProductDetail p2 = new ProductDetail("2", "Dress", BigDecimal.valueOf(19.99), true);
        ProductDetail p3 = new ProductDetail("3", "Blazer", BigDecimal.valueOf(29.99), false);

        when(getSimilarProductsUseCase.getSimilarProducts(productId)).thenReturn(List.of(p2, p3));

        mockMvc.perform(get("/product/{productId}/similar", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("2"))
                .andExpect(jsonPath("$[0].name").value("Dress"))
                .andExpect(jsonPath("$[0].price").value(19.99))
                .andExpect(jsonPath("$[0].availability").value(true))
                .andExpect(jsonPath("$[1].id").value("3"))
                .andExpect(jsonPath("$[1].name").value("Blazer"))
                .andExpect(jsonPath("$[1].price").value(29.99))
                .andExpect(jsonPath("$[1].availability").value(false));
    }

    @Test
    void getSimilarProducts_whenNotFound_shouldReturn404() throws Exception {
        String productId = "999";

        when(getSimilarProductsUseCase.getSimilarProducts(productId)).thenThrow(new ProductNotFoundException(productId));

        mockMvc.perform(get("/product/{productId}/similar", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product not found with id: 999"));
    }
}
