package br.dev.kevin.example.resource;

import br.dev.kevin.example.dto.ProductDto;
import br.dev.kevin.example.service.ProductService;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/product")
@ApplicationScoped
public class ProductResource {

    @Inject
    ProductService productService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<ProductDto>> test() {
        return productService.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Void> test(@Valid ProductDto productDto) {
        return productService.save(productDto);
    }
}