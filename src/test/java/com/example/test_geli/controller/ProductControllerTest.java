package com.example.test_geli.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.example.test_geli.dto.CreateProductDto;
import com.example.test_geli.dto.CreateProductDto.ProductVariantDto;
import com.example.test_geli.dto.UpdateStockDto;
import com.example.test_geli.repository.ProductRepository;
import com.github.javafaker.Faker;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void createProduct() {
        Faker faker = new Faker();
        CreateProductDto productDto = new CreateProductDto();
        productDto.setName(faker.commerce().productName());
        productDto.setDescription(faker.lorem().sentence());
        productDto.setSku("SKU-" + System.currentTimeMillis());

        List<ProductVariantDto> variants = new ArrayList<>();
        ProductVariantDto variant = new ProductVariantDto();
        variant.setName(faker.commerce().productName());
        variant.setPrice(BigDecimal.valueOf(29.99));
        variant.setStock(100);
        variant.setColor(faker.color().name());
        variant.setSize("M");
        variants.add(variant);
        productDto.setProductVariants(variants);

        HttpEntity<CreateProductDto> request = new HttpEntity<>(productDto);
        ResponseEntity<Map> response = this.restTemplate.exchange("/api/v1/product", HttpMethod.POST, request, Map.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().get("message")).isEqualTo("Product has been created");
    }

    @Test
    public void testGetProducts() {
        ResponseEntity<Map> response = this.restTemplate.exchange("/api/v1/product?page=0&size=10", HttpMethod.GET, null, Map.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    public void getProductDetail() {
        var products = productRepository.findAll();
        if (!products.isEmpty()) {
            Long productId = products.get(0).getId();
            ResponseEntity<Map> response = this.restTemplate.exchange("/api/v1/product/" + productId, HttpMethod.GET, null, Map.class);
            assertThat(response.getStatusCode().value()).isEqualTo(200);
        }
    }

    @Test
    public void updateProduct() {
        Faker faker = new Faker();
        CreateProductDto productDto = new CreateProductDto();
        productDto.setName(faker.commerce().productName()+ " Updated");
        productDto.setDescription(faker.lorem().sentence());
        productDto.setSku("SKU-GET-" + System.currentTimeMillis());
        var products = productRepository.findAll();
        HttpEntity<CreateProductDto> createRequest = new HttpEntity<>(productDto);
        ResponseEntity<Map> createResponse = this.restTemplate.exchange("/api/v1/product/" + products.get(0).getId() , HttpMethod.PUT, createRequest, Map.class);
        assertThat(createResponse.getBody().toString()).contains("Product has been updated");
    }

    @Test
    public void deleteProduct() {
        var products = productRepository.findAll();
        if (!products.isEmpty()) {
            Long productId = products.get(0).getId();
            ResponseEntity<String> response = this.restTemplate.exchange("/api/v1/product/destroy/" + productId, HttpMethod.DELETE, new HttpEntity<>(null), String.class);
            assertThat(response.getBody().toString()).contains("Product has been deleted");
        }
    }
}
