package br.dev.kevin.example.service;

import br.dev.kevin.example.dto.ProductDto;
import br.dev.kevin.example.repository.ProductRepository;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    @Inject
    Mutiny.Session session;

    public Uni<List<ProductDto>> findAll() {
        return productRepository.findAll();
    }

    public Uni<Void> save(ProductDto productDto) {
        return session.withTransaction((t) ->
                productRepository.save(productDto)
                        .invoke(e -> { /** ON ERROR: Rollback transaction - throw new IllegalStateException("");**/})
        );
    }
}
