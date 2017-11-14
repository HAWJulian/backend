package com.example.shop.controller;

public class SettingsDTO
{
    //Strats
    private int combineStrat;
    private int mutationStrat;
    private int fitnessStrat;
    private int terminationStrat;
    //TODO Strat specific settings

    //Elite
    private boolean elite;
    private int eliteSize;
    //General
    private int populationSize;
    private float mutationRate;
    private float selectionWeight;

    public int getCombineStrat()
    {
        return combineStrat;
    }

    public void setCombineStrat(int combineStrat)
    {
        this.combineStrat = combineStrat;
    }

    public int getMutationStrat()
    {
        return mutationStrat;
    }

    public void setMutationStrat(int mutationStrat)
    {
        this.mutationStrat = mutationStrat;
    }

    public int getFitnessStrat()
    {
        return fitnessStrat;
    }

    public void setFitnessStrat(int fitnessStrat)
    {
        this.fitnessStrat = fitnessStrat;
    }

    public int getTerminationStrat()
    {
        return terminationStrat;
    }

    public void setTerminationStrat(int terminationStrat)
    {
        this.terminationStrat = terminationStrat;
    }

    public boolean isElite()
    {
        return elite;
    }

    public void setElite(boolean elite)
    {
        this.elite = elite;
    }

    public int getEliteSize()
    {
        return eliteSize;
    }

    public void setEliteSize(int eliteSize)
    {
        this.eliteSize = eliteSize;
    }

    public int getPopulationSize()
    {
        return populationSize;
    }

    public void setPopulationSize(int populationSize)
    {
        this.populationSize = populationSize;
    }

    public float getMutationRate()
    {
        return mutationRate;
    }

    public void setMutationRate(float mutationRate)
    {
        this.mutationRate = mutationRate;
    }

    public float getSelectionWeight()
    {
        return selectionWeight;
    }

    public void setSelectionWeight(float selectionWeight)
    {
        this.selectionWeight = selectionWeight;
    }

    @Override
    public String toString()
    {
        return "SettingsDTO{" +
                "combineStrat=" + combineStrat +
                ", mutationStrat=" + mutationStrat +
                ", fitnessStrat=" + fitnessStrat +
                ", terminationStrat=" + terminationStrat +
                ", elite=" + elite +
                ", eliteSize=" + eliteSize +
                ", populationSize=" + populationSize +
                ", mutationRate=" + mutationRate +
                ", selectionWeight=" + selectionWeight +
                '}';
    }
}
