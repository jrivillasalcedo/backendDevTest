package com.example.similar_products.infrastructure.adapter.in.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ApiCatalogController {

    private final AtomicInteger cartCounter = new AtomicInteger(0);
    private static final Map<String, Map<String, Object>> PRODUCTS_DB = new ConcurrentHashMap<>();

    static {
        initSampleProducts();
    }

    private static void initSampleProducts() {
        addProduct(
            "1",
            "Apple",
            "iPhone 15 Pro",
            "1199",
            "https://images.unsplash.com/photo-1695048133142-1a20484d2569?w=600&auto=format&fit=crop&q=80",
            "Apple A17 Pro (3 nm)",
            "8 GB RAM",
            "iOS 17",
            "1179 x 2556 pixels",
            "6.1 inches",
            "Li-Ion 3274 mAh",
            List.of("48 MP Principal", "12 MP Teleobjetivo 3x", "12 MP Ultra Gran Angular"),
            List.of("12 MP HDR 4K@60fps"),
            "146.6 x 70.6 x 8.3 mm",
            "187 g",
            List.of(
                Map.of("code", 1000, "name", "Titanio Natural"),
                Map.of("code", 1001, "name", "Titanio Azul"),
                Map.of("code", 1002, "name", "Titanio Negro"),
                Map.of("code", 1003, "name", "Titanio Blanco")
            ),
            List.of(
                Map.of("code", 2000, "name", "128 GB"),
                Map.of("code", 2001, "name", "256 GB"),
                Map.of("code", 2002, "name", "512 GB"),
                Map.of("code", 2003, "name", "1 TB")
            )
        );

        addProduct(
            "2",
            "Samsung",
            "Galaxy S24 Ultra",
            "1349",
            "https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?w=600&auto=format&fit=crop&q=80",
            "Qualcomm Snapdragon 8 Gen 3 (4 nm)",
            "12 GB RAM",
            "Android 14, One UI 6.1",
            "1440 x 3120 pixels Dynamic AMOLED 2X",
            "6.8 inches",
            "Li-Ion 5000 mAh carga rápida 45W",
            List.of("200 MP Principal", "50 MP Periscopio 5x", "10 MP Teleobjetivo 3x", "12 MP Gran Angular"),
            List.of("12 MP Dual Pixel PDAF"),
            "162.3 x 79 x 8.6 mm",
            "232 g",
            List.of(
                Map.of("code", 1000, "name", "Titanium Gray"),
                Map.of("code", 1001, "name", "Titanium Black"),
                Map.of("code", 1002, "name", "Titanium Violet")
            ),
            List.of(
                Map.of("code", 2000, "name", "256 GB"),
                Map.of("code", 2001, "name", "512 GB"),
                Map.of("code", 2002, "name", "1 TB")
            )
        );

        addProduct(
            "3",
            "Google",
            "Pixel 8 Pro",
            "999",
            "https://images.unsplash.com/photo-1598327105666-5b89351aff97?w=600&auto=format&fit=crop&q=80",
            "Google Tensor G3 (4 nm)",
            "12 GB RAM",
            "Android 14 actualizable",
            "1344 x 2992 pixels LTPO OLED",
            "6.7 inches",
            "Li-Ion 5050 mAh carga rápida 30W",
            List.of("50 MP Principal", "48 MP Teleobjetivo 5x", "48 MP Ultra Gran Angular"),
            List.of("10.5 MP Ultrawide"),
            "162.6 x 76.5 x 8.8 mm",
            "213 g",
            List.of(
                Map.of("code", 1000, "name", "Obsidian"),
                Map.of("code", 1001, "name", "Porcelain"),
                Map.of("code", 1002, "name", "Bay Blue")
            ),
            List.of(
                Map.of("code", 2000, "name", "128 GB"),
                Map.of("code", 2001, "name", "256 GB"),
                Map.of("code", 2002, "name", "512 GB")
            )
        );

        addProduct(
            "4",
            "Xiaomi",
            "14 Ultra",
            "1299",
            "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=600&auto=format&fit=crop&q=80",
            "Qualcomm Snapdragon 8 Gen 3",
            "16 GB RAM",
            "Xiaomi HyperOS (Android 14)",
            "1440 x 3200 pixels LTPO AMOLED",
            "6.73 inches",
            "Li-Po 5000 mAh carga 90W",
            List.of("50 MP Óptica Leica 1\"", "50 MP Teleobjetivo", "50 MP Periscopio", "50 MP Gran Angular"),
            List.of("32 MP Gran Angular"),
            "161.4 x 75.3 x 9.2 mm",
            "219.8 g",
            List.of(
                Map.of("code", 1000, "name", "Negro Cuero Vegano"),
                Map.of("code", 1001, "name", "Blanco Cerámica")
            ),
            List.of(
                Map.of("code", 2000, "name", "512 GB")
            )
        );

        addProduct(
            "100",
            "OnePlus",
            "12",
            "899",
            "https://images.unsplash.com/photo-1580910051074-3eb694886505?w=600&auto=format&fit=crop&q=80",
            "Snapdragon 8 Gen 3",
            "16 GB RAM",
            "OxygenOS 14",
            "1440 x 3168 pixels",
            "6.82 inches",
            "5400 mAh SUPERVOOC 100W",
            List.of("50 MP Sony LYT-808", "64 MP Periscopio 3x", "48 MP Ultra Gran Angular"),
            List.of("32 MP"),
            "164.3 x 75.8 x 9.15 mm",
            "220 g",
            List.of(
                Map.of("code", 1000, "name", "Silky Black"),
                Map.of("code", 1001, "name", "Flowy Emerald")
            ),
            List.of(
                Map.of("code", 2000, "name", "256 GB"),
                Map.of("code", 2001, "name", "512 GB")
            )
        );
    }

    private static void addProduct(
        String id,
        String brand,
        String model,
        String price,
        String imgUrl,
        String cpu,
        String ram,
        String os,
        String displayResolution,
        String displaySize,
        String battery,
        List<String> primaryCamera,
        List<String> secondaryCamera,
        String dimensions,
        String weight,
        List<Map<String, Object>> colors,
        List<Map<String, Object>> storages
    ) {
        Map<String, Object> product = new HashMap<>();
        product.put("id", id);
        product.put("brand", brand);
        product.put("model", model);
        product.put("price", price);
        product.put("imgUrl", imgUrl);
        product.put("cpu", cpu);
        product.put("ram", ram);
        product.put("os", os);
        product.put("displayResolution", displayResolution);
        product.put("displaySize", displaySize);
        product.put("battery", battery);
        product.put("primaryCamera", primaryCamera);
        product.put("secondaryCmera", secondaryCamera);
        product.put("dimentions", dimensions);
        product.put("weight", weight);
        product.put("options", Map.of(
            "colors", colors,
            "storages", storages
        ));

        PRODUCTS_DB.put(id, product);
    }

    @GetMapping("/product")
    public ResponseEntity<List<Map<String, Object>>> getProductsList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map<String, Object> full : PRODUCTS_DB.values()) {
            Map<String, Object> item = new HashMap<>();
            item.put("id", full.get("id"));
            item.put("brand", full.get("brand"));
            item.put("model", full.get("model"));
            item.put("price", full.get("price"));
            item.put("imgUrl", full.get("imgUrl"));
            list.add(item);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable String productId) {
        Map<String, Object> product = PRODUCTS_DB.get(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cart")
    public ResponseEntity<Map<String, Object>> addToCart(@RequestBody Map<String, Object> payload) {
        int currentCount = cartCounter.incrementAndGet();
        return ResponseEntity.ok(Map.of("count", 1, "total", currentCount));
    }
}
