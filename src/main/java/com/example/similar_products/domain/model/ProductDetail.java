package com.example.similar_products.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductDetail(
    @NotBlank
    @JsonProperty("id")
    String id,

    @NotBlank
    @JsonProperty("name")
    String name,

    @NotNull
    @JsonProperty("price")
    BigDecimal price,

    @NotNull
    @JsonProperty("availability")
    Boolean availability
) {}
