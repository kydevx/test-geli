package com.example.test_geli.dto;

import java.math.BigDecimal;
import java.util.List;

public class CreateProductDto {
    private String name;
    private String description;
    private String sku;
    private List<ProductVariantDto> productVariants;

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public List<ProductVariantDto> getProductVariants() {
        return this.productVariants;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProductVariants(List<ProductVariantDto> productVariants) {
        this.productVariants = productVariants;
    }

    public String getSku() {
        return this.sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public static class ProductVariantDto {
        private String name;
        private BigDecimal price;
        private Integer stock;
        private String color;
        private String size;

        public String getName() {
            return this.name;
        }

        public BigDecimal getPrice() {
            return this.price;
        }

        public Integer getStock() {
            return this.stock;
        }

        public String getColor() {
            return this.color;
        }

        public String getSize() {
            return this.size;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}
