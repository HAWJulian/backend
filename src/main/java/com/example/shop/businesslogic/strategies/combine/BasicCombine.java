package com.example.shop.businesslogic.strategies.combine;

import com.example.shop.businesslogic.ga.DNA;

public class BasicCombine extends CombineStrategy
{
    @Override
    public DNA combine(DNA parent1, DNA parent2)
    {
        DNA child = new DNA(parent1.getGenes().length, parent1.getReducedIdOrder());
        // TODO midpoint?
        int midpoint = rdm.nextInt(parent1.getGenes().length);
        for (int i = 0; i < parent1.getGenes().length; i++)
        {
            if (i < midpoint)
            {
                child.getGenes()[i] = parent1.getGenes()[i];
            }
            else
            {
                child.getGenes()[i] = parent2.getGenes()[i];
            }
        }
        child.calculateTranslation();
        return child;
    }
}
