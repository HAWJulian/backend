package com.example.shop.controller;

import com.example.shop.entities.SupermarketNode;

import java.util.ArrayList;
//Object hält den @RequestBody des CalculationControllers
//Es darf nur ein @RequestBody existieren. Müssen mehrere Variablen übergeben werden, muss ein DataTransferObject gebildet werden.
public class CalculationDTO
{
    //Entweder ID oder supermarket
    private long supermarketId;
    private ArrayList<SupermarketNode> supermarket;
    private ArrayList<Long> shoppingCart;
    private  SettingsDTO settingsDTO;

    public SettingsDTO getSettingsDTO()
    {
        return settingsDTO;
    }

    public void setSettingsDTO(SettingsDTO settingsDTO)
    {
        this.settingsDTO = settingsDTO;
    }

    public long getSupermarketId()
    {
        return supermarketId;
    }

    public void setSupermarketId(long supermarketId)
    {
        this.supermarketId = supermarketId;
    }

    public ArrayList<Long> getShoppingCart()
    {
        return shoppingCart;
    }

    public void setShoppingCart(ArrayList<Long> shoppingCart)
    {
        this.shoppingCart = shoppingCart;
    }

    public ArrayList<SupermarketNode> getSupermarket()
    {
        return supermarket;
    }

    public void setSupermarket(ArrayList<SupermarketNode> supermarket)
    {
        this.supermarket = supermarket;
    }
}
