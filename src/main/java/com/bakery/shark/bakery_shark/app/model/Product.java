package com.bakery.shark.bakery_shark.app.model;

import com.bakery.shark.bakery_shark.app.validation.NotZeroDoubleValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table( name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotZeroDoubleValidator
    @Column(name = "price", nullable = false, precision = 2)
    private double price;

//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "recipe_id")
//    private Recipe recipe;

    @ManyToOne
    private Recipe recipe;

    @Lob
    @Column(name = "photo", columnDefinition="MEDIUMBLOB")
    private byte[] photo;

    public Product() {
    }

}
