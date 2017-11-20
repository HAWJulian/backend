package com.example.shop.businesslogic.strategies.fitnesscalculator;

import com.example.shop.businesslogic.ga.DNA;
import com.example.shop.businesslogic.graphgen.Graph;

import java.util.ArrayList;

public abstract class AbstractFitnessCalculator
{
    Graph g;
    public AbstractFitnessCalculator()
    {

    }
    public AbstractFitnessCalculator(Graph g)
    {
        this.g = g;
    }
    public abstract void setup(ArrayList<Float> weights);
    public abstract float calculateFitness(DNA dna);
}
