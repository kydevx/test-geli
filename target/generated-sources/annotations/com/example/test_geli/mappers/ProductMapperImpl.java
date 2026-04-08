package com.example.test_geli.mappers;

import com.example.test_geli.dto.ProductResponseDto;
import com.example.test_geli.dto.ProductVariantResponseDto;
import com.example.test_geli.entity.ProductEntity;
import com.example.test_geli.entity.ProductVariantEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-08T19:33:58+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponseDto toDto(ProductEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ProductResponseDto productResponseDto = new ProductResponseDto();

        productResponseDto.setId( entity.getId() );
        productResponseDto.setName( entity.getName() );
        productResponseDto.setDescription( entity.getDescription() );
        productResponseDto.setSku( entity.getSku() );
        productResponseDto.setCreatedAt( entity.getCreatedAt() );
        productResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return productResponseDto;
    }

    @Override
    public List<ProductResponseDto> toDtos(List<ProductEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProductResponseDto> list = new ArrayList<ProductResponseDto>( entities.size() );
        for ( ProductEntity productEntity : entities ) {
            list.add( toDto( productEntity ) );
        }

        return list;
    }

    @Override
    public ProductVariantResponseDto toVariantDto(ProductVariantEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ProductVariantResponseDto productVariantResponseDto = new ProductVariantResponseDto();

        productVariantResponseDto.setId( entity.getId() );
        productVariantResponseDto.setName( entity.getName() );
        productVariantResponseDto.setPrice( entity.getPrice() );
        productVariantResponseDto.setStock( entity.getStock() );
        productVariantResponseDto.setColor( entity.getColor() );
        productVariantResponseDto.setSize( entity.getSize() );

        return productVariantResponseDto;
    }
}
