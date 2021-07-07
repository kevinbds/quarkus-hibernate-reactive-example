package br.dev.kevin.example.mapper;

import br.dev.kevin.example.dto.ProductDto;
import br.dev.kevin.example.model.ProductEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface ProductMapper {

    ProductDto toMap(ProductEntity productEntity);

    ProductEntity toMap(ProductDto productDto);

    @AfterMapping
    default void afterMapping(@MappingTarget ProductEntity productEntity) {

        productEntity.getOffers().forEach(e -> {
            e.setProduct(productEntity);
            e.getPrices().forEach(f -> {
                f.setOffer(e);
                f.getVersion().setPrice(f);
            });
        });

    }
}
