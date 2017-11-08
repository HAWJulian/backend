package com.example.shop.controller;

import java.util.ArrayList;
//Object hält den @RequestBody des CalculationControllers
//Es darf nur ein @RequestBody existieren. Müssen mehrere Variablen übergeben werden, muss ein DataTransferObject gebildet werden.
public class CalculationDTO
{
    private long supermarketId;
    private ArrayList<Long> shoppingCart;

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
}
