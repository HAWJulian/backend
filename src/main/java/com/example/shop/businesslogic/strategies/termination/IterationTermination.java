package com.example.shop.businesslogic.strategies.termination;

import com.example.shop.businesslogic.ga.DNA;

import java.util.ArrayList;

public class IterationTermination extends TerminationStrategy
{
    int amtIterations;
    public IterationTermination(int param)
    {
        super(param);
        this.amtIterations = 0;
    }
    @Override
    public void improvement(DNA improvement)
    {

    }
    @Override
    public void update(ArrayList<DNA> generation)
    {
        this.amtIterations++;
    }

    @Override
    public boolean checkTermination()
    {
        return (this.amtIterations>=terminationCondition);
    }
}
