package com.example.shop.controller;

import java.util.ArrayList;

public class SettingsDTO
{
    //Strats
    //Switch case @Population
    private int combineStrat;
    private int mutationStrat;
    private int fitnessStrat;
    private int terminationStrat;
    //TODO more strat specific settings?
    private ArrayList<Float> fitnessParam;
    private int terminationParam;

    //Elite
    //Gibt es eine Elite?
    private boolean elite;
    //Wenn ja, wie viele Elemente?
    //Darf nicht größer sein als populationSize
    private int eliteSize;
    //General
    //Anzahl DNA in einer Generation
    private int populationSize;
    //Wahrscheinlichkeit der Mutation
    private float mutationRate;
    //Gewichtung bei der Selektion - 0 = keine Gewichtung, 1 = linear
    private float selectionWeight;
    //region getterandsetter
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

    public int getTerminationParam()
    {
        return terminationParam;
    }

    public void setTerminationParam(int terminationParam)
    {
        this.terminationParam = terminationParam;
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

    public ArrayList<Float> getFitnessParam()
    {
        return fitnessParam;
    }

    public void setFitnessParam(ArrayList<Float> fitnessParam)
    {
        this.fitnessParam = fitnessParam;
    }

    //endregion

    @Override
    public String toString()
    {
        return "SettingsDTO{" +
                "combineStrat=" + combineStrat +
                ", mutationStrat=" + mutationStrat +
                ", fitnessStrat=" + fitnessStrat +
                ", terminationStrat=" + terminationStrat +
                ", fitnessParam=" + fitnessParam +
                ", terminationParam=" + terminationParam +
                ", elite=" + elite +
                ", eliteSize=" + eliteSize +
                ", populationSize=" + populationSize +
                ", mutationRate=" + mutationRate +
                ", selectionWeight=" + selectionWeight +
                '}';
    }
}
