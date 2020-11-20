package com.bakery.shark.bakery_shark.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "ingredientsQuantities")
public class WorkIngredientQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    @Column(nullable = false, unique = true)
    private String workIngredientName;

    @Column(precision = 2, nullable = false)
    private double workIngredientQuantity;

    public WorkIngredientQuantity() {
    }
}
