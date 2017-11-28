package com.example.shop.businesslogic.strategies.replacement;

import com.example.shop.businesslogic.ga.DNA;

import java.util.ArrayList;
import java.util.Collections;

public class BestIndividualsReplacement extends ReplacementStrategy
{
    @Override
    public ArrayList<DNA> replace(ArrayList<DNA> parents, ArrayList<DNA> children)
    {
        int populationsize = parents.size();
        parents.addAll(children);
        Collections.sort(parents);
        //parents.subList(parents.size()/2-1,parents.size()-1).clear();
        ArrayList<DNA> nextGen = new ArrayList<>();
        while(nextGen.size() < populationsize)
        {
            DNA nextDNA = parents.get(0);
            parents.remove(nextDNA);
            if(!nextGen.contains(nextDNA))
            {
                nextGen.add(nextDNA);
            }
        }
        return nextGen;
    }
}
