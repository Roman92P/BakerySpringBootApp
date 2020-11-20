package com.bakery.shark.bakery_shark.app.model;

import com.bakery.shark.bakery_shark.app.validation.RecipeValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@Table(name = "recipes")
@Getter
@Setter
@GroupSequence({RecipeValid.class, Recipe.class})
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "You should enter the name of new recipe!",groups = {RecipeValid.class})
    private String name;

    @Size(min=1, message = "You should add at least one recipe item", groups = Recipe.class)
    @OneToMany(mappedBy = "recipe",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<RecipeItem> recipeItemList = new HashSet<>();

    public Recipe() {
    }
}
