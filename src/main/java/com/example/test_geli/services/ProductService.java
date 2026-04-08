package com.example.test_geli.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.test_geli.dto.CreateProductDto;
import com.example.test_geli.dto.ProductResponseDto;
import com.example.test_geli.entity.ProductEntity;
import com.example.test_geli.entity.ProductVariantEntity;
import com.example.test_geli.exception.BusinessException;
import com.example.test_geli.mappers.ProductMapper;
import com.example.test_geli.repository.ProductRepository;
import com.example.test_geli.repository.ProductVariantRepository;
import org.springframework.data.domain.Page;

@Service
public class ProductService {
    final private ProductRepository productRepository;
    final private ProductVariantRepository productVariantRepository;
    final private ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductVariantRepository productVariantRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
        this.productMapper = productMapper;
    }

    public Object getProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return this.productRepository.findAll(pageRequest).map(u -> this.productMapper.toDto(u));
    }

    public ProductResponseDto getProductById(Long id) {
        ProductEntity product = this.productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "Product not found"));
        return this.productMapper.toDto(product);
    }

    @Transactional
    public String createProduct(CreateProductDto createProduct) {
        if (this.productRepository.existsBySku(createProduct.getSku())) {
            throw new BusinessException(400, "Product with SKU " + createProduct.getSku() + " already exists");
        }

        ProductEntity product = new ProductEntity();
        product.setName(createProduct.getName());
        product.setDescription(createProduct.getDescription());
        product.setSku(createProduct.getSku());

        final ProductEntity savedProduct = this.productRepository.save(product);

        if (createProduct.getProductVariants() != null) {
            createProduct.getProductVariants().forEach(variantDto -> {
                ProductVariantEntity variant = new ProductVariantEntity();
                variant.setName(variantDto.getName());
                variant.setPrice(variantDto.getPrice());
                variant.setStock(variantDto.getStock());
                variant.setColor(variantDto.getColor());
                variant.setSize(variantDto.getSize());
                variant.setProduct(savedProduct);
                this.productVariantRepository.save(variant);
            });
        }
        return  "Product has been created";
    }

    @Transactional
    public String updateProduct(Long id, CreateProductDto updateDto) {
        ProductEntity product = this.productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "Product not found"));

        product.setName(updateDto.getName());
        product.setDescription(updateDto.getDescription());
        
        this.productRepository.save(product);
        return "Product has been updated";
    }

    @Transactional
    public String deleteProduct(Long id) {
        if (!this.productRepository.existsById(id)) {
            throw new BusinessException(404, "Product not found");
        }
        this.productRepository.deleteById(id);
        return "Product has been deleted";
    }

    @Transactional
    public String updateVariantStock(Long variantId, Integer quantity) {
        ProductVariantEntity variant = this.productVariantRepository.findById(variantId)
                .orElseThrow(() -> new BusinessException(404, "Variant not found"));

        int newStock = variant.getStock() + quantity;
        if (newStock < 0) {
            throw new BusinessException(400, "Insufficient stock. Current stock: " + variant.getStock());
        }

        variant.setStock(newStock);
        this.productVariantRepository.save(variant);
        return "Stock updated successfully. New stock: " + variant.getStock();
    }
}
