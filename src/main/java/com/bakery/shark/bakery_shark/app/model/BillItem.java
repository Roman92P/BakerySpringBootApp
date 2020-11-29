package com.bakery.shark.bakery_shark.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
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

    @Digits(integer = 8, fraction = 2)
    private double soldProductPrice;

    @Min(value = 1)
    private int soldProductQuantity;

    @ManyToOne
    private Bill bill;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public BillItem() {
    }
}
