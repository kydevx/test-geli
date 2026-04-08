package com.example.test_geli.mappers;

import com.example.test_geli.dto.ProductResponseDto;
import com.example.test_geli.dto.ProductVariantResponseDto;
import com.example.test_geli.entity.ProductEntity;
import com.example.test_geli.entity.ProductVariantEntity;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDto toDto(ProductEntity entity);

    List<ProductResponseDto> toDtos(List<ProductEntity> entities);

    ProductVariantResponseDto toVariantDto(ProductVariantEntity entity);
}
