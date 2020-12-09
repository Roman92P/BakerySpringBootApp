package com.bakery.shark.bakery_shark.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Setter
@Getter
public class LostProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double value;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock lostStock;

    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
    }

    public LostProducts() {
    }

}
