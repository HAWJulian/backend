package com.example.shop.businesslogic.strategies.fitnesscalculator;

import com.example.shop.businesslogic.ga.DNA;
import com.example.shop.businesslogic.graphgen.Edge;
import com.example.shop.businesslogic.graphgen.Graph;
import com.example.shop.businesslogic.graphgen.Node;
import com.example.shop.entities.Article;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public abstract class AbstractFitnessCalculator
{
    Graph g;
    static final int fridgeCoolingValue = 1;
    static final int freezerCoolingValue = 5;
    ArrayList<Integer> coolingValues;
    int bestCaseCooling;
    int worstCaseCooling;
    public AbstractFitnessCalculator(Graph g)
    {
        this.g = g;
        coolingValues = new ArrayList<Integer>();
        for (Node n : g.getNodes())
        {
            //Summiert alle cooling werte der Artikel einer Node
            int coolingValueNode = 0;
            for (Article a : n.getArticles())
            {
                if(a.getCooling() == 1)
                {
                    coolingValueNode+=this.fridgeCoolingValue;
                }
                else if(a.getCooling() == 2)
                {
                    coolingValueNode+=this.freezerCoolingValue;
                }
            }
            coolingValues.add(coolingValueNode);
        }

        calculatePercentageCoolingValues();
        System.out.println("cooling values: " + coolingValues);
        System.out.println("best csse cooling: " + bestCaseCooling);
        System.out.println("worst case cooling: " + worstCaseCooling);
    }
    //Berechnet int werte für bestcase und worstcase cooling um einen prozentualen wert einer coolingorder einer dna zu berechnen
    private void calculatePercentageCoolingValues()
    {
        bestCaseCooling =0;
        Collections.sort(coolingValues);
        System.out.println(coolingValues);
        for(int i = 0; i < coolingValues.size(); i++)
        {
            bestCaseCooling+=(i+1) * coolingValues.get(i);
        }
        worstCaseCooling = 0;
        Collections.sort(coolingValues);
        Collections.reverse(coolingValues);
        System.out.println(coolingValues);
        for(int i = 0; i < coolingValues.size(); i++)
        {
            worstCaseCooling+= (i+1) * coolingValues.get(i);
        }
    }
    public abstract void setup(ArrayList<Float> weights);
    public abstract float calculateFitness(DNA dna);
    public abstract void evaluateGeneration(ArrayList<DNA> generation);
    public int calculatePathlength(DNA dna)
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
        return pathlength;
    }
    public int calculateCooling(DNA dna)
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
                    nodeCooling+=this.fridgeCoolingValue;
                }
                else if(a.getCooling() == 2)
                {
                    nodeCooling+=this.freezerCoolingValue;
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
        return dnaCooling;
    }
}
