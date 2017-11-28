package com.example.shop.businesslogic.strategies.combine;

import com.example.shop.businesslogic.ga.DNA;

import java.util.Random;

public class UniformCombine extends CombineStrategy
{
    @Override
    public DNA combine(DNA partnerA, DNA partnerB)
    {
        Random r = new Random();
        boolean[] mask = new boolean[partnerA.getGenes().length];
        for (int i = 0; i < mask.length; i++)
        {
            mask[i] = r.nextBoolean();
        }
        DNA child = new DNA(partnerA.getGenes().length, partnerA.getReducedIdOrder());
        Integer[] childGenes = new Integer[partnerA.getTranslation().length];
        for (int i = 0; i < mask.length; i++)
        {
            if(mask[i])
            {
                childGenes[i] = partnerA.getGenes()[i];
            }
            else
            {
                childGenes[i] = partnerB.getGenes()[i];
            }
        }
        System.out.println(mask.length);
        System.out.println(partnerA.getGenes().length);

        child.setGenes(childGenes);
        child.calculateTranslation();
        return child;
    }
}
