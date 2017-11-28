package com.example.shop.businesslogic.strategies.replacement;

import com.example.shop.businesslogic.ga.DNA;

import java.util.ArrayList;
import java.util.Random;

public class RandomReplacement extends ReplacementStrategy
{
    @Override
    public ArrayList<DNA> replace(ArrayList<DNA> parents, ArrayList<DNA> children)
    {
        int popsize = parents.size();
        Random r = new Random();
        parents.addAll(children);
        ArrayList<DNA> selection = new ArrayList<>();
        while(selection.size()<popsize)
        {
            DNA rdmDNA = parents.get(r.nextInt(parents.size()));
            if(!selection.contains(rdmDNA))
            {
                selection.add(rdmDNA);
            }
            parents.remove(rdmDNA);
        }
        return selection;
    }
}
