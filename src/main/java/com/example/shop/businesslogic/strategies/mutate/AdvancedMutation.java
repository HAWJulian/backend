package com.example.shop.businesslogic.strategies.mutate;

import com.example.shop.businesslogic.ga.DNA;

public class AdvancedMutation extends MutateStrategy
{
    @Override
    public DNA mutate(DNA dna, float mutationRate)
    {
        for (int i = 1; i < dna.getGenes().length - 1; i++)
        {
            //Zufällige Zahl darf length-position nicht überschreiten
            //da in Genes die letzte Zahl immer 1 ist und die davor liegenden Positionen nie auf das letzte Element
            //zugreifen dürfen (höchster Werte in Genes von rechts nach links: 1,1,2,3,4,5,6...)
            if (rdm.nextFloat() < mutationRate)
            {
                dna.getGenes()[i] = 1+rdm.nextInt(dna.getGenes().length-i-1);
            }
        }
        dna.calculateTranslation();
        return dna;
    }
}
