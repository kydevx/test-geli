package com.example.test_geli.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.test_geli.aspect.response.Response;
import com.example.test_geli.dto.CreateProductDto;
import com.example.test_geli.dto.ProductResponseDto;
import com.example.test_geli.dto.UpdateStockDto;
import com.example.test_geli.services.ProductService;

@RestController
@RequestMapping(value = "${api}/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    @Response(url = "/product")
    public Object getProducts(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(this.productService.getProducts(page, size));
    }

    @GetMapping(value = "/{id}")
    @Response
    public Object getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(this.productService.getProductById(id));
    }

    @PostMapping
    @Response
    public Object createProduct(@RequestBody CreateProductDto createProduct) {
        return ResponseEntity.ok(this.productService.createProduct(createProduct));
    }

    @PutMapping(value = "/{id}")
    @Response
    public Object updateProduct(@PathVariable Long id, @RequestBody CreateProductDto updateDto) {
        return ResponseEntity.ok(this.productService.updateProduct(id, updateDto));
    }

    @DeleteMapping(value = "/destroy/{id}")
    @Response
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(this.productService.deleteProduct(id));
    }

    @PatchMapping(value = "/variant/{variantId}/stock")
    @Response
    public Object updateStock(@PathVariable Long variantId, @RequestBody UpdateStockDto updateStockDto) {
        return ResponseEntity.ok(this.productService.updateVariantStock(variantId, updateStockDto.getQuantity()));
    }
}