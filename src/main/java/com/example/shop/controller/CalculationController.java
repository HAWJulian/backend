package com.example.shop.controller;

import com.example.shop.businesslogic.ga.DNA;
import com.example.shop.businesslogic.ga.ResultDTO;
import com.example.shop.services.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class CalculationController
{
    @Autowired
    CalculationService calculationService;

    @PostMapping(value="/calculation")
    //public void startCalculation(@RequestBody Long supermarketId, @RequestBody ArrayList<Long> shoppingCart)
    public ArrayList<ResultDTO> startCalculation(@RequestBody CalculationDTO dto)
    {
        System.out.println("dto debug print");
        System.out.println(dto.getSettingsDTO());
        return calculationService.startCalculation(dto.getSupermarketId(), dto.getShoppingCart(), dto.getSettingsDTO());
    }

    @PostMapping(value="/editedCalculation")
    public ArrayList<ResultDTO> startEditedCalculation(@RequestBody CalculationDTO calculationDTO)
    {
        System.out.println("editedCalc");
        System.out.println(calculationDTO.getSettingsDTO());
        return calculationService.startEditedCalculation(calculationDTO.getSupermarket(),calculationDTO.getShoppingCart(), calculationDTO.getSettingsDTO());
    }
}
