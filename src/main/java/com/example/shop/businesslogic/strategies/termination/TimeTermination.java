package com.example.shop.businesslogic.strategies.termination;

import com.example.shop.businesslogic.ga.DNA;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class TimeTermination extends TerminationStrategy
{
    private Instant start;
    public TimeTermination(int param)
    {
        super(param);
        start = Instant.now();
    }

    @Override
    public void update(ArrayList<DNA> generation)
    {

    }
    @Override
    public void improvement(DNA improvement)
    {

    }
    @Override
    public boolean checkTermination()
    {
        return (Duration.between(start, Instant.now()).getSeconds() >= terminationCondition);
    }
}
