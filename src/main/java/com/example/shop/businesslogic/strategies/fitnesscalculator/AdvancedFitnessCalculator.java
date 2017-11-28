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
    //Werte die pro fridge/freezer Produkt einer Node summiert werden (NOCOOLING = 0)

    int sumEdges = 0;
    ArrayList<Integer> coolingValues;
    int bestCaseCooling;
    int worstCaseCooling;
    private float plWeight;
    private float coolingWeight;

    public AdvancedFitnessCalculator(Graph g)
    {
        super(g);
        for (Edge e : g.getEdges())
        {
            sumEdges+=e.getWeight();
        }
        System.out.println("sumEdges: " + sumEdges);
        //Erwartet einen Graphen, in dem alle Artikel in den Nodes des Graphen auch gekauft werden
        coolingValues = new ArrayList<Integer>();
        for (Node n : g.getNodes())
        {
            //Summiert alle cooling werte der Artikel einer Node
            int coolingValueNode = 0;
            for (Article a : n.getArticles())
            {
                if(a.getCooling() == 1)
                {
                    coolingValueNode+=super.fridgeCoolingValue;
                }
                else if(a.getCooling() == 2)
                {
                    coolingValueNode+=super.freezerCoolingValue;
                }
            }
            coolingValues.add(coolingValueNode);
        }

        calculateBestCaseCooling();
        calculateWorstCaseCooling();
    }
    @Override
    public void setup(ArrayList<Float> weights)
    {
        plWeight = weights.get(0);
        coolingWeight = weights.get(1);
    }
    private void calculateBestCaseCooling()
    {
        bestCaseCooling =0;
        Collections.sort(coolingValues);
        System.out.println(coolingValues);
        for(int i = 0; i < coolingValues.size(); i++)
        {
            bestCaseCooling+=(i+1) * coolingValues.get(i);
        }
        System.out.println(bestCaseCooling);
    }
    private void calculateWorstCaseCooling()
    {
        worstCaseCooling = 0;
        Collections.sort(coolingValues);
        Collections.reverse(coolingValues);
        System.out.println(coolingValues);
        for(int i = 0; i < coolingValues.size(); i++)
        {
            worstCaseCooling+= (i+1) * coolingValues.get(i);
        }
        System.out.println(worstCaseCooling);
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
        coolingValue = (coolingValue-worstCaseCooling)/(float) (bestCaseCooling-worstCaseCooling);
        int fit = Math.round(x*plWeight+x*coolingValue*coolingWeight);

        dna.setFitness(fit);
        return fit;
    }

    @Override
    public void evaluateGeneration(ArrayList<DNA> generation)
    {

    }
}
