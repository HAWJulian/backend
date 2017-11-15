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
    static final int fridgeCoolingValue = 1;
    static final int freezerCoolingValue = 5;
    int sumEdges = 0;
    ArrayList<Integer> coolingValues;
    int bestCaseCooling;
    int worstCaseCooling;
    float plWeight = 0.9998f;
    float coolingWeight = 0.0002f;

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
                    coolingValueNode+=fridgeCoolingValue;
                }
                else if(a.getCooling() == 2)
                {
                    coolingValueNode+=freezerCoolingValue;
                }
            }
            coolingValues.add(coolingValueNode);
        }

        calculateBestCaseCooling();
        calculateWorstCaseCooling();
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
    private float calculateCoolingValue(ArrayList<Integer> coolingOrder)
    {
        //Hält den absoluten Punktewert, der die Reihenfolge der besuchten Nodes in Hinsicht auf
        //die benötigte Kühlung bewertet
        int dnaCooling = 0;
        for(int i = 0; i < coolingOrder.size(); i++)
        {
            dnaCooling+=(i+1) * coolingOrder.get(i);
        }
        float result = (float) (dnaCooling-worstCaseCooling)/(float) (bestCaseCooling-worstCaseCooling);
        //System.out.println(result);
        return result;
    }
    //Modell: a-pl = x
    //> x * plgewichtung + x * (0-1) * (gewichtung) + x * (0-1) * (gewichtung)
    //> Summe aller Gewichtungen = 1
    //Somit kann der Fitnesswert den Wert x nicht überschreiten
    @Override
    public float calculateFitness(DNA dna)
    {
        int pathlength = 0;
        for (int i = 0; i < dna.getGenes().length - 1; i++)
        {
            Node start = g.getNodeById(dna.getTranslation()[i]);
            Node end = g.getNodeById(dna.getTranslation()[i + 1]);
            for (Edge e : g.getEdges())
            {
                // Überprüft die objectid, da Objekte u.U. kopiert worden sind
                // (und somit die überprüfung auf die Objektinstanz nicht
                // erfolgreich ist)
                if (e.containsNodeId(start.getObjectId()) && e.containsNodeId(end.getObjectId()))
                {
                    pathlength += e.getWeight();
                }
            }
        }
        dna.setPathLength(pathlength);
        //War vorher (sumedges - pathlength), muss lediglich eine große Zahl - fit stehen,
        //damit der absolute Unterschied der Fitnesswerte verschwindend gering ist
        //
        int x = 100000 - pathlength;
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
        //System.out.println(coolingOrder);
        float coolingValue = calculateCoolingValue(coolingOrder);
        int fit = Math.round(x*plWeight+x*coolingValue*coolingWeight);
		/*
		System.out.println("base: " + x + "(100.000-" + pathlength+") = " + (100000-pathlength));
		System.out.println("x*plweight+x*coolingvalue*coolingweight");
		System.out.println((100000-pathlength) +"*"+plWeight+"+"+(100000-pathlength)+"*"+coolingValue+"*"+coolingWeight);
		System.out.println(fit);
		*/
        dna.setFitness(fit);
        return fit;
    }
}
