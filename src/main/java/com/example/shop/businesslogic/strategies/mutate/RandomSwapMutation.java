package com.example.shop.businesslogic.strategies.mutate;

import com.example.shop.businesslogic.ga.DNA;
//Vertauscht in mutationRate% der DNA genau zwei Einträge einer DNA
public class RandomSwapMutation extends MutateStrategy
{
    @Override
    public DNA mutate(DNA dna, float mutationRate)
    {
        if(rdm.nextFloat()<mutationRate)
        {
            //Random index zwischen 2. und vorletzter Position (exklusive 1. und letzter position)
            int startSwap = 1+rdm.nextInt(dna.getGenes().length-2);
            int endSwap = 1+rdm.nextInt(dna.getGenes().length-2);
            int temp = dna.getTranslation()[startSwap];
            dna.getTranslation()[startSwap] = dna.getTranslation()[endSwap];
            dna.getTranslation()[endSwap] = temp;
            dna.calculateGenes();
        }
        return dna;
    }
}
