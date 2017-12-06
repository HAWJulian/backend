package com.example.shop.businesslogic.strategies.fitnesscalculator;

import com.example.shop.businesslogic.ga.DNA;
import com.example.shop.businesslogic.graphgen.Edge;
import com.example.shop.businesslogic.graphgen.Graph;
import com.example.shop.businesslogic.graphgen.Node;
import com.example.shop.entities.Article;

import java.util.ArrayList;
import java.util.Collections;

public class AdvancedFitnessCalculator extends AbstractFitnessCalculator
{
    private float plWeight;
    private float coolingWeight;

    public AdvancedFitnessCalculator(Graph g)
    {
        super(g);
        //Erwartet einen Graphen, in dem alle Artikel in den Nodes des Graphen auch gekauft werden

    }
    @Override
    public void setup(ArrayList<Float> weights)
    {
        plWeight = weights.get(0);
        coolingWeight = weights.get(1);
    }

    //Modell: a-pl = x
    //> x * plgewichtung + x * (0-1) * (gewichtung) + x * (0-1) * (gewichtung)
    //> Summe aller Gewichtungen = 1
    //Somit kann der Fitnesswert den Wert x nicht überschreiten
    @Override
    public float calculateFitness(DNA dna)
    {
        int pathlength = super.calculatePathlength(dna);
        dna.setPathLength(pathlength);
        //War vorher (sumedges - pathlength), muss lediglich eine große Zahl - fit stehen,
        //damit der absolute Unterschied der Fitnesswerte verschwindend gering ist
        //
        int x = 100000 - pathlength;
        //System.out.println(coolingOrder);
        float coolingValue = super.calculateCooling(dna);
        coolingValue = (coolingValue-super.worstCaseCooling)/(float) (super.bestCaseCooling-super.worstCaseCooling);
        int fit = Math.round(x*plWeight+x*coolingValue*coolingWeight);
        int cooling = super.calculateCooling(dna);
        dna.setCooling(cooling);
        dna.setCoolingPercent((float)cooling-super.worstCaseCooling/(super.bestCaseCooling-super.worstCaseCooling));
        dna.setFitness(fit);
        return fit;
    }

    @Override
    public void evaluateGeneration(ArrayList<DNA> generation)
    {

    }
}
