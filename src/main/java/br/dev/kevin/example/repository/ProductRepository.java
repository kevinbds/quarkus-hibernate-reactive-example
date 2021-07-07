package br.dev.kevin.example.repository;

import br.dev.kevin.example.dto.ProductDto;
import br.dev.kevin.example.mapper.ProductMapper;
import br.dev.kevin.example.model.OfferEntity;
import br.dev.kevin.example.model.PriceEntity;
import br.dev.kevin.example.model.ProductEntity;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ProductRepository {

    @Inject
    Mutiny.Session session;

    @Inject
    ProductMapper productMapper;

    public Uni<List<ProductDto>> findAll() {
        return session.createNamedQuery("findAll", ProductEntity.class)
                .getResultList()
                .onItem()
                .<ProductEntity>disjoint()
                .call(e -> session.fetch(e.getOffers())
                        .onItem()
                        .<OfferEntity>disjoint()
                        .call(o -> session.fetch(o.getPrices())
                                .onItem()
                                .<PriceEntity>disjoint()
                                .call(p -> session.fetch(p.getVersion()))
                                .collect()
                                .asList())
                        .collect()
                        .asList())
                .map(productMapper::toMap)
                .collect()
                .asList();

    }

    public Uni<Void> save(ProductDto productDto) {
        ProductEntity productEntity = productMapper.toMap(productDto);
        return session.withTransaction((t) -> session.persist(productEntity));
    }

}
