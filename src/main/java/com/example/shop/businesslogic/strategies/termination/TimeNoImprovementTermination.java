package com.example.shop.businesslogic.strategies.termination;

import com.example.shop.businesslogic.ga.DNA;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class TimeNoImprovementTermination extends TerminationStrategy
{
    int currentBestFitness;
    Instant lastNew;
    public TimeNoImprovementTermination(int param)
    {
        super(param);
        this.currentBestFitness = 0;
        lastNew = Instant.now();
    }

    @Override
    public void update(ArrayList<DNA> generation)
    {
        if(generation.get(0).getFitness() > currentBestFitness)
        {
            currentBestFitness = (int) generation.get(0).getFitness();
            lastNew = Instant.now();
        }
    }

    @Override
    public boolean checkTermination()
    {
        return (Duration.between(lastNew, Instant.now()).getSeconds() >= terminationCondition);
    }
}
