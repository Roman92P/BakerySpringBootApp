package com.bakery.shark.bakery_shark.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Table(name = "Produced")
public class Produced {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productName;

    private double productPrice;

    private int productStockQuantity;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    public Produced() {
    }

    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
    }
}
