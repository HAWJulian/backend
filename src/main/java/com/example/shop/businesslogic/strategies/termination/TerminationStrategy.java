package com.example.shop.businesslogic.strategies.termination;

import com.example.shop.businesslogic.ga.DNA;

import java.util.ArrayList;

public abstract class TerminationStrategy
{
    int terminationCondition;
    public TerminationStrategy(int param)
    {
        this.terminationCondition = param;
    }
    public abstract void update(ArrayList<DNA> generation);
    public abstract boolean checkTermination();
}
