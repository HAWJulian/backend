package com.example.shop.businesslogic.strategies.fitnesscalculator;

import com.example.shop.businesslogic.ga.DNA;
import com.example.shop.businesslogic.graphgen.Edge;
import com.example.shop.businesslogic.graphgen.Graph;
import com.example.shop.businesslogic.graphgen.Node;

import java.util.ArrayList;

public class BasicFitnessCalculator extends AbstractFitnessCalculator
{
    public BasicFitnessCalculator(Graph g)
    {
        super(g);
    }

    @Override
    public float calculateFitness(DNA dna)
    {
        // Fit entspricht zunächst der Länge des Pfades, die Fitness wird als
        // Inverse dazu definiert
        // damit eine größere Fitness einem besseren Pfad entspricht

        int fit = super.calculatePathlength(dna);
        dna.setPathLength(fit);
        float fitness = (1f / fit);
        dna.setFitness(fitness);
        return fitness;
    }

    @Override
    public void evaluateGeneration(ArrayList<DNA> generation)
    {

    }

    @Override
    public void setup(ArrayList<Float> weights)
    {

    }
}
