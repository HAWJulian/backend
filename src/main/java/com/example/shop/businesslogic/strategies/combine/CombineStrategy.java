package com.example.shop.businesslogic.strategies.combine;

import com.example.shop.businesslogic.ga.DNA;

import java.util.Random;

public abstract class CombineStrategy
{
    protected Random rdm;
    public CombineStrategy()
    {
        rdm = new Random();
    }
    public abstract DNA combine(DNA partnerA, DNA partnerB);
}
