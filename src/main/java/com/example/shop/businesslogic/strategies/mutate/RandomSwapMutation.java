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
            System.out.println("mutate!");
            System.out.println(dna);
            int startSwap = rdm.nextInt(dna.getGenes().length);
            int endSwap = rdm.nextInt(dna.getGenes().length);
            int temp = dna.getTranslation()[startSwap];
            dna.getTranslation()[startSwap] = dna.getTranslation()[endSwap];
            dna.getTranslation()[endSwap] = temp;
            System.out.println(dna);
            dna.calculateGenes();
        }
        return dna;
    }
}
