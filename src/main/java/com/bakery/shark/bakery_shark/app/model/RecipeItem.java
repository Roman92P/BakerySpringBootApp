package com.bakery.shark.bakery_shark.app.model;

import com.bakery.shark.bakery_shark.app.validation.NotZeroDoubleValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "recipeItem")
public class RecipeItem {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne
    private Ingredient ingredients;

    @NotZeroDoubleValidator
    @Column(precision = 2)
    private Double ingredientQuantity;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Recipe recipe;

    public RecipeItem() {
    }
}
