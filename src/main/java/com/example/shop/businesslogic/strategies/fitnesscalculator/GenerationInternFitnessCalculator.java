package com.example.shop.businesslogic.strategies.fitnesscalculator;

import com.example.shop.businesslogic.ga.DNA;
import com.example.shop.businesslogic.graphgen.Edge;
import com.example.shop.businesslogic.graphgen.Graph;
import com.example.shop.businesslogic.graphgen.Node;
import com.example.shop.entities.Article;

import java.util.ArrayList;

public class GenerationInternFitnessCalculator extends AbstractFitnessCalculator
{
    static final int fridgeCoolingValue = 1;
    static final int freezerCoolingValue = 5;
    //Best values of current generation
    int bestPath;
    int worstPath;
    int bestCooling;
    int worstCooling;
    //
    private float plWeight;
    private float coolingWeight;
    public GenerationInternFitnessCalculator(Graph g)
    {
        super(g);
    }
    @Override
    public void setup(ArrayList<Float> weights)
    {
        plWeight = weights.get(0);
        coolingWeight = weights.get(1);
        System.out.println("weights: " + weights.get(0) + ", " + weights.get(1));
    }
    public void evaluateGeneration(ArrayList<DNA> generation)
    {
        //Path soll minimiert werden
        this.bestPath = Integer.MAX_VALUE;
        this.worstPath = 0;
        //Cooling soll maximiert werden
        this.bestCooling = 0;
        this.worstCooling = Integer.MAX_VALUE;
        calculatePathlengths(generation);
        calculateCooling(generation);
    }
    private void calculatePathlengths(ArrayList<DNA> generation)
    {
        for (DNA dna : generation)
        {
            int pathlength = 0;
            for (int i = 0; i < dna.getGenes().length - 1; i++)
            {
                Node start = g.getNodeById(dna.getTranslation()[i]);
                Node end = g.getNodeById(dna.getTranslation()[i + 1]);
                for (Edge e : g.getEdges())
                {
                    if (e.containsNodeId(start.getObjectId()) && e.containsNodeId(end.getObjectId()))
                    {
                        pathlength += e.getWeight();
                    }
                }
            }
            dna.setPathLength(pathlength);
            if(pathlength < this.bestPath)
            {
                this.bestPath = pathlength;
            }
            if(pathlength > this.worstPath)
            {
                this.worstPath = pathlength;
            }
        }
        System.out.println("best pathlength after evaluation: " + this.bestPath);
        System.out.println("worst pathlength after evaluation: " + this.worstPath);
    }
    private void calculateCooling(ArrayList<DNA> generation)
    {
        for (DNA dna : generation)
        {
            ArrayList<Integer> coolingOrder = new ArrayList<Integer>();
            for (Integer i : dna.getTranslation())
            {
                Node n = g.getNodeById(i);
                //Summe der Kühlfaktoren in einer Node, bestimmt über die in der Node liegenden Artikel
                int nodeCooling = 0;
                for (Article a : n.getArticles())
                {
                    if(a.getCooling() == 1)
                    {
                        nodeCooling+=fridgeCoolingValue;
                    }
                    else if(a.getCooling() == 2)
                    {
                        nodeCooling+=freezerCoolingValue;
                    }
                }
                coolingOrder.add(nodeCooling);
            }
            dna.setCoolingOrder(coolingOrder);
            int dnaCooling = 0;
            for(int i = 0; i < coolingOrder.size(); i++)
            {
                dnaCooling+=(i+1) * coolingOrder.get(i);
            }
            dna.setCooling(dnaCooling);
            if(dnaCooling > this.bestCooling)
            {
                this.bestCooling = dnaCooling;
            }
            if(dnaCooling < this.worstCooling)
            {
                this.worstCooling = dnaCooling;
            }
        }
        System.out.println("best coolinig after evaluation: " + this.bestCooling);
        System.out.println("worst cooling after evaluation: " + this.worstCooling);
    }
    @Override
    public float calculateFitness(DNA dna)
    {
        // PL: (worst - current)/(worst - best)
        float plFitness = (float)(this.worstPath - dna.getPathLength()) / (float)(this.worstPath - this.bestPath);
        //System.out.println("plfitness: " + plFitness);
        //Cooling: (current - worst)/(best - worst)
        float coolingFitness = (float)(dna.getCooling() - this.worstCooling) / (float)(this.bestCooling - this.worstCooling);
        //System.out.println("coolingfitness: " + coolingFitness);
        //Multiplication with 1000 for better visibility
        float fitness = (plFitness*plWeight+coolingFitness*coolingWeight)*1000;
        /*
        System.out.println("fitness: " + fitness);
        System.out.println(dna.getPathLength());
        */
        dna.setFitness(fitness);
        return fitness;
    }
}