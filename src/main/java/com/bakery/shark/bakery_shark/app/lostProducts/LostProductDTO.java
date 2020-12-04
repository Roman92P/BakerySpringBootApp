package com.bakery.shark.bakery_shark.app.lostProducts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class LostProductDTO {

    private String createdOn;
     private double value;

    public LostProductDTO() {
    }
}
