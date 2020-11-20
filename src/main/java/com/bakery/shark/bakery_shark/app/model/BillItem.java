package com.bakery.shark.bakery_shark.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Table(name = "bill_item")
public class BillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String soldProductName;

    private double soldProductPrice;

    @Min(value = 1)
    private int soldProductQuantity;

    @ManyToOne
    private Bill bill;

    public BillItem() {
    }
}
