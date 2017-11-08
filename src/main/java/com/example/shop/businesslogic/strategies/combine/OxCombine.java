package com.example.shop.businesslogic.strategies.combine;

import com.example.shop.businesslogic.ga.DNA;

import java.util.ArrayList;
import java.util.Arrays;

public class OxCombine extends CombineStrategy
{

    @Override
    public DNA combine(DNA partnerA, DNA partnerB)
    {
        int length = partnerA.getTranslation().length;
        DNA child = new DNA(length, partnerA.getReducedIdOrder());
        int m1 = length/3;
        int m2 = 2*m1;
        int parentIterator = 2*m1;
        int splitpoint=0;
        //Schritt 1
        for(int i = m1; i < m2; i++)
        {
            child.getTranslation()[i] = partnerA.getTranslation()[i];
        }
        //Schritt 2
        //(Arrays.asList(child.getGenes()).contains(parent.getGenes()[parentIterator]))
        ArrayList<Integer> parentHelper = new ArrayList<Integer>();
        for(int i = 0; i < length; i++)
        {
            if(!Arrays.asList(child.getTranslation()).contains(partnerB.getTranslation()[i]))
            {
                if(splitpoint==0 && i >=m2)
                {
                    splitpoint = parentHelper.size();
                }
                parentHelper.add(partnerB.getTranslation()[i]);
            }
        }
        ArrayList<Integer> sl = new ArrayList<Integer>();
        sl.addAll(parentHelper.subList(splitpoint, parentHelper.size()));
        parentHelper.removeAll(sl);
        sl.addAll(parentHelper);
        parentHelper.clear();
        parentHelper.addAll(sl);
        child.getTranslation()[partnerA.getTranslation().length-1] = partnerA.getTranslation()[partnerA.getTranslation().length-1];
        child.getTranslation()[0] = 0;
        parentHelper.remove(new Integer(0));
        parentHelper.remove(new Integer(partnerA.getTranslation()[partnerA.getTranslation().length-1]));
        while(parentHelper.size()>0)
        {
            if(child.getTranslation()[parentIterator]==null)
            {
                child.getTranslation()[parentIterator]=parentHelper.get(0);
                parentHelper.remove(0);
            }
            parentIterator++;
            parentIterator = (parentIterator%length);
        }
        child.calculateGenes();
        return child;
    }
}
