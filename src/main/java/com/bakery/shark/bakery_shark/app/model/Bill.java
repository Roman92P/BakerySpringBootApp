package com.bakery.shark.bakery_shark.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @OneToMany(mappedBy = "bill")
    private List<BillItem> allBillItemList = new ArrayList<>();

    private double sumOfCustomerOrder;

    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
    }

    public Bill() {
    }
}
