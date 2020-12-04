package com.bakery.shark.bakery_shark.app.lostProducts;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/lostProducts")
public class LostProductsRestController {

    private final Gson gson;
    private final LostProductService lostProductService;

    @RequestMapping("/lostMoney/{startDate}/{endDate}")
    public String getAllLostProductsValue(@PathVariable String startDate, @PathVariable String endDate){
        List<Object[]> lpList = lostProductService.getAllLostsBetweenDates(startDate, endDate);
        List<LostProductDTO> lpDTOList = new ArrayList<>();
        for (Object[] lp : lpList){
            LostProductDTO lostProductDTO = new LostProductDTO();
            Date lpCreated =(Date) lp[0];
            lostProductDTO.setCreatedOn(lpCreated.toString());
            lostProductDTO.setValue((Double)lp[1]);
            lpDTOList.add(lostProductDTO);
        }
        return gson.toJson(lpDTOList);
    }
}
