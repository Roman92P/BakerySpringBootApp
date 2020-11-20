package com.bakery.shark.bakery_shark.app.model;

import com.bakery.shark.bakery_shark.app.validation.IngredientQuantity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Table(name = "ingredients")
@IngredientQuantity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", unique = true, nullable = false)
    @NotBlank
    private String name;

    @NotNull
    @Column(name = "weight", precision = 2)
    private Double weight;

    @NotNull
    @Column(name="quantity", precision = 2)
    private Double quantity;

    @NotNull
    @Column(name = "liters", precision = 2)
    private Double litr;

    public Ingredient() {
    }

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany( mappedBy = "ingredients")
    private Set<RecipeItem> recipes=new HashSet<>();

}
