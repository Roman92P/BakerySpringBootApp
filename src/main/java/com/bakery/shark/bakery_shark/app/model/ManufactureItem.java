package com.bakery.shark.bakery_shark.app.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Table(name = "manufactureItems")
public class ManufactureItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    @ManyToOne
    private Manufactured manufactured;

    @Min(value = 1)
    @NotNull
    @Column(name = "quantity", nullable = false)
    private int quantity;

    public ManufactureItem() {
    }
}
