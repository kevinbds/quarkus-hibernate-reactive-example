package br.dev.kevin.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double price;

    @ManyToOne
    private OfferEntity offer;

    @OneToOne(mappedBy = "price", cascade = CascadeType.ALL)
    private PriceVersionEntity version;

}
