package br.dev.kevin.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String offerName;

    @ManyToOne
    private ProductEntity product;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL)
    private List<PriceEntity> prices;
}
