package com.example.shop.businesslogic.strategies.fitnesscalculator;

import com.example.shop.businesslogic.ga.DNA;
import com.example.shop.businesslogic.graphgen.Edge;
import com.example.shop.businesslogic.graphgen.Graph;
import com.example.shop.businesslogic.graphgen.Node;

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

        int fit = 0;
        for (int i = 0; i < dna.getGenes().length - 1; i++)
        {
            Node start = g.getNodeById(dna.getTranslation()[i]);
            // SupermarketNode start = g.getNodes().get(dna.getTranslation()[i]);
            Node end = g.getNodeById(dna.getTranslation()[i + 1]);
            // SupermarketNode end = g.getNodes().get(dna.getTranslation()[i+1]);
            for (Edge e : g.getEdges())
            {
                // Überprüft die objectid, da Objekte u.U. kopiert worden sind
                // (und somit die überprüfung auf die Objektinstanz nicht
                // erfolgreich ist)
                if (e.containsNodeId(start.getObjectId()) && e.containsNodeId(end.getObjectId()))
                {
                    fit += e.getWeight();
                }
            }
        }
        dna.setPathLength(fit);
        float fitness = (1f / fit) * 100 * dna.getGenes().length;
        dna.setFitness(fitness);
        return fitness;
    }
}
