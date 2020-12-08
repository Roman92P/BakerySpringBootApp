package com.bakery.shark.bakery_shark.app.adminDashboard;

import com.bakery.shark.bakery_shark.app.cashRegister.JpaBillItemService;
import com.bakery.shark.bakery_shark.app.cashRegister.JpaBillService;
import com.bakery.shark.bakery_shark.app.model.BillDto;
import com.bakery.shark.bakery_shark.app.model.BillItemDTO;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/dashboard")
public class AdminDashboardRestController {

    private final JpaBillService jpaBillService;
    private final JpaBillItemService jpaBillItemService;
    private final Gson gson;

    @GetMapping(value = "/soldCurrentMonth",produces ="text/plain;charset=UTF-8")
    public String getSoldInCurentMonth(){
        List<Object[]> billsForCurrentMonth = jpaBillService.getBillsForCurrentMonth();
        List<BillDto>bills = new ArrayList<>();
        for (Object[] bill : billsForCurrentMonth ){
            BillDto billDto = new BillDto();
            Date billDate = (Date) bill[0];
            billDto.setDate( billDate.toString());
            billDto.setSum((Double)bill[1]);
            bills.add(billDto);
        }
        return gson.toJson(bills);
    }

    @GetMapping("/soldPreviousMonth")
    public String getSoldInPreviousMonth(){
        List<Object[]> billsForPreviousMonth = jpaBillService.getBillsForPreviousMonth();
        List<BillDto>bills = new ArrayList<>();
        for (Object[] bill : billsForPreviousMonth ){
            BillDto billDto = new BillDto();
            Date billDate = (Date) bill[0];
            billDto.setDate( billDate.toString());
            billDto.setSum((Double)bill[1]);
            bills.add(billDto);
        }
        return gson.toJson(bills);
    }

    @GetMapping("/customSoldDate/{start}/{end}")
    public String getSoldFromCustomPeriod(@PathVariable String start, @PathVariable String end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(start, formatter);
        LocalDate localDate1 = LocalDate.parse(end, formatter);
        List<Object[]> billsForCertainPeriod = jpaBillService.getBillsForCertainPeriod(localDate,localDate1);
        List<BillDto> bills = new ArrayList<>();
        for ( Object[] bill : billsForCertainPeriod ) {
            BillDto billDto = new BillDto();
            Date billDate = (Date) bill[0];
            billDto.setDate(billDate.toString());
            billDto.setSum((Double) bill[1]);
            bills.add(billDto);
        }
        return gson.toJson(bills);
    }

    @GetMapping(value = "/mostPopularProducts/{year}/{month}", produces = "text/plain;charset=UTF-8")
    public String getMostPopularProductByDate(@PathVariable String year, @PathVariable String month){

        List<Object[]> soldNamesAndQuantitiesForPeriod = jpaBillItemService.getSoldNamesAndQuantitiesForPeriod(year, month);
        List<BillItemDTO> billItemDTOList = new ArrayList<>();
        for(Object[] billItem : soldNamesAndQuantitiesForPeriod){
            BillItemDTO billItemDTO = new BillItemDTO();
            billItemDTO.setSoldProductName((String)billItem[0]);
            billItemDTO.setSoldProductQuantity((Integer)billItem[1]);
            billItemDTOList.add(billItemDTO);
        }
        return gson.toJson(billItemDTOList);
    }

}
