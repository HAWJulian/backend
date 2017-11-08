package com.example.shop.businesslogic.strategies.mutate;

import com.example.shop.businesslogic.ga.DNA;

public class BasicMutation extends MutateStrategy
{
    @Override
    public DNA mutate(DNA dna, float mutationRate)
    {
        //Lässt Index 0 aus und läuft nur bis zum vorvorletzten Element
        //Damit die 1. SupermarketNode an Stelle 1 und die letzte SupermarketNode an letzter Stelle bleiben
        for (int i = 1; i < dna.getTranslation().length - 2; i++)
        {
            if (rdm.nextFloat() < mutationRate)
            {
                int temp = dna.getTranslation()[i];
                dna.getTranslation()[i] = dna.getTranslation()[i + 1];
                dna.getTranslation()[i + 1] = temp;
            }
        }
        dna.calculateGenes();
        return dna;
    }
}
