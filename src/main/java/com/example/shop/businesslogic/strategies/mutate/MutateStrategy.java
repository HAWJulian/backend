package com.example.shop.businesslogic.strategies.mutate;

import com.example.shop.businesslogic.ga.DNA;

import java.util.Random;

public abstract class MutateStrategy
{
    protected Random rdm;
    public MutateStrategy()
    {
        rdm = new Random();
    }
    public abstract DNA mutate(DNA dna, float mutationRate);
}
