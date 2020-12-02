package com.bakery.shark.bakery_shark.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Getter
@Setter
@Component
public class BillItemDTO {

    private String soldProductName;
    private int soldProductQuantity;

    public BillItemDTO() {
    }
}
