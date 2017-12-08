package com.example.shop.businesslogic.strategies.termination;

import com.example.shop.businesslogic.ga.DNA;

import java.util.ArrayList;

public class NoImprovementTermination extends TerminationStrategy
{

    float currentBestFitness;
    int noImprovementIterations;
    public NoImprovementTermination(int param)
    {
        super(param);
        this.currentBestFitness = 0;
        this.noImprovementIterations = 0;
    }

    @Override
    public void update(ArrayList<DNA> generation)
    {
        /*
        if(generation.get(0).getFitness() > currentBestFitness)
        {
            currentBestFitness = generation.get(0).getFitness();
            noImprovementIterations = 0;
        }
        else
        {
            noImprovementIterations++;
        }
        */
        noImprovementIterations++;
    }

    @Override
    public void improvement(DNA improvement)
    {
        noImprovementIterations=0;
    }
    @Override
    public boolean checkTermination()
    {
        return (noImprovementIterations>=terminationCondition);
    }
}
