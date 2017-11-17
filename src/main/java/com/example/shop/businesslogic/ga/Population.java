package com.example.shop.businesslogic.ga;

import com.example.shop.businesslogic.graphgen.Graph;
import com.example.shop.businesslogic.graphgen.Node;
import com.example.shop.businesslogic.strategies.combine.BasicCombine;
import com.example.shop.businesslogic.strategies.combine.CombineStrategy;
import com.example.shop.businesslogic.strategies.combine.OxCombine;
import com.example.shop.businesslogic.strategies.fitnesscalculator.AbstractFitnessCalculator;
import com.example.shop.businesslogic.strategies.fitnesscalculator.AdvancedFitnessCalculator;
import com.example.shop.businesslogic.strategies.fitnesscalculator.BasicFitnessCalculator;
import com.example.shop.businesslogic.strategies.mutate.AdvancedMutation;
import com.example.shop.businesslogic.strategies.mutate.BasicMutation;
import com.example.shop.businesslogic.strategies.mutate.MutateStrategy;
import com.example.shop.businesslogic.strategies.termination.*;
import com.example.shop.controller.SettingsDTO;
import com.example.shop.entities.Article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population
{
    float mutationrate;
    int currentBestGeneration = 0;
    ArrayList<DNA> population;
    ArrayList<DNA> matingPool;
    int generations;
    int populationSize;
    Graph g;
    float currentBestFitness;
    Random rdm;
    AbstractFitnessCalculator afc;
    CombineStrategy cs;
    MutateStrategy ms;
    TerminationStrategy ts;
    ArrayList<Integer> reducedIdOrder;
    //Settings
    //Elite
    boolean elite = true;
    int eliteSize = 1;
    //Strategies
    int mutationStrat = 0;
    int combineStrat = 0;
    int fitnessStrat = 0;
    int terminationStrat = 0;
    float selectionWeight = 1f;


    //public Population(float mutationrate, int amtNodes, int popsize, Graph g)
    public Population(int amtNodes, Graph g, SettingsDTO settingsDTO)
    {
        rdm = new Random();
        population = new ArrayList<DNA>();
        matingPool = new ArrayList<DNA>();
        reducedIdOrder = new ArrayList<Integer>();
        generations = 0;
        //Apply settings
        this.g = g;
        this.mutationrate = settingsDTO.getMutationRate();
        this.populationSize = settingsDTO.getPopulationSize();
        this.combineStrat = settingsDTO.getCombineStrat();
        this.mutationStrat = settingsDTO.getMutationStrat();
        this.fitnessStrat = settingsDTO.getFitnessStrat();
        //TODO implement terminationStrat
        //#iterations no improvement
        //#iterations
        //t no improvement
        //t

        this.terminationStrat = settingsDTO.getTerminationStrat();
        this.selectionWeight = settingsDTO.getSelectionWeight();
        this.elite = settingsDTO.isElite();
        this.eliteSize = settingsDTO.getEliteSize();
        System.out.println("Settings testprint");
        System.out.println(this);
        //Startnode
        Node startNode = g.getStartNode();
        //Endnode
        Node endNode = g.getEndNode();
        reducedIdOrder.add(startNode.getObjectId());
        for (Node n : g.getNodes())
        {
            if(!n.equals(startNode) && !n.equals(endNode))
            {
                reducedIdOrder.add(n.getObjectId());
            }
        }
        //Endnode
        reducedIdOrder.add(g.getEndNode().getObjectId());
        System.out.println("Reduced Id order: " + reducedIdOrder);
        //Strategy selection
        switch (combineStrat)
        {
            case 0:
                System.out.println("case 0");
                cs = new BasicCombine();
                break;
            case 1:
                System.out.println("case 1");
                cs = new OxCombine();
                break;
            default:
                System.out.println("default");
                cs = new BasicCombine();
                break;
        }
        switch (mutationStrat)
        {
            case 0:
                ms = new BasicMutation();
                break;
            case 1:
                ms = new AdvancedMutation();
                break;
            default:
                ms = new BasicMutation();
                break;
        }
        switch (fitnessStrat)
        {
            case 0:
                afc = new BasicFitnessCalculator(g);
                break;
            case 1:
                afc = new AdvancedFitnessCalculator(g);
                break;
            default:
                afc = new BasicFitnessCalculator(g);
                break;
        }
        System.out.println("termination strat");
        switch (terminationStrat)
        {
            case 0:
                System.out.println("0");
                ts = new NoImprovementTermination(settingsDTO.getTerminationParam());
                break;
            case 1:
                System.out.println("1");
                ts = new IterationTermination(settingsDTO.getTerminationParam());
                break;
            case 2:
                System.out.println("2");
                ts = new TimeTermination(settingsDTO.getTerminationParam());
                break;
            case 3:
                System.out.println("3");
                ts = new TimeNoImprovementTermination(settingsDTO.getTerminationParam());
                break;
            default:
                System.out.println("default");
                ts = new NoImprovementTermination(100);
                break;
        }
        for (int i = 0; i < populationSize; i++)
        {
            DNA toAdd = new DNA(amtNodes, reducedIdOrder);
            toAdd.randomize();
            population.add(toAdd);
        }
        // Berechnet die Fitness aller DNA in population
        calculateFitness();
        // Sortiert die Population nach der Fitness (aufsteigend)
        Collections.sort(population);
        currentBestFitness = population.get(0).getFitness();
        // System.out.println(currentBestFitness);
        // generations - currentBestGeneration < 400


    }

    private void calculateFitness()
    {
        for (DNA dna : population)
        {
            afc.calculateFitness(dna);
        }
    }
    public DNA execute()
    {
        while (!ts.checkTermination())
        {
            System.out.println("Iteration: " + generations);
            generateNewGeneration();
            ts.update(population);
        }

        System.out.println("best result: " + currentBestFitness + " in " + generations + " iterations");
        System.out.println(population.get(0));
        System.out.println("total amt generations " + generations);
        return population.get(0);
    }
    // Alternative Implementierungsmöglichkeiten:
    // Schlechte DNA nicht dem matingpool hinzufügen
    // Gewichtung anders umsetzen
    // ...
    private void selection()
    {
        matingPool.clear();
        // Hinzfügen von DNA der aktuellen Generation in Abhängigkeit von der
        // Fitness der DNA
        // System.out.println("populationsize: " + population.size());
        for (int i = 0; i < population.size(); i++)
        {
            // float relativeFitness = 1+remap(population.get(i).getFitness(),
            // 0, currentBestFitness, 1,0);
            float relativeFitness = remap(population.get(i).getFitness(), currentBestFitness,
                    population.get(populationSize - 1).getFitness(), 1, 0);
            // System.out.println("relativeFitness: " + relativeFitness);
            long n = Math.round(Math.pow(relativeFitness, selectionWeight) * 100);
            for (int j = 0; j < n; j++)
            {
                matingPool.add(population.get(i));
            }
        }
    }

    // Mapt einen Wert (toMap) eines Intervalls auf einen Wert in einem neuen
    // Intervall
    private float remap(float toMap, float previousLow, float previousHigh, float low, float high)
    {
        // System.out.println(toMap + ", " + previousLow + ", " + previousHigh
        // +", " + low + ", " + high);
        // Fallunterscheidung redundant, wenn dafür gesorgt wird, dass
        // previousLow!=previousHigh
        // -> vermutlich fixed, wenn überprüft wird, dass eine bereits
        // existierende DNA nicht nochmals zur neuen generation hinzugefügt wird
        // ^ trifft zwar zu, ist jedoch durch den code nicht garantiert,
        // anweisung sollte somit bestehen bleiben
        if (previousLow == previousHigh)
        {
            return 1;
        }
        return low + (toMap - previousLow) * (high - low) / (previousHigh - previousLow);
    }

    private void generateNewGeneration()
    {
        selection();
        ArrayList<DNA> nextGeneration = new ArrayList<DNA>();
        //Eliten übertragung
        if(elite)
        {
            for(int i = 0; i < eliteSize; i++)
            {
                nextGeneration.add(population.get(i));
            }
        }
        // Fügt neue child DNA der population hinzu
        // Dafür werden zufällige parentDNA aus dem matingpool selektiert
        // for(int i = 1; i<population.size(); i++)
        while (nextGeneration.size() < population.size())
        {
            //System.out.println("start combine");
            DNA partnerA = matingPool.get(rdm.nextInt(matingPool.size()));
            DNA partnerB = matingPool.get(rdm.nextInt(matingPool.size()));
            DNA child = cs.combine(partnerA, partnerB);
            child = ms.mutate(child, this.mutationrate);
            // Keine Duplikate zulassen, damit die Kombination in späteren
            // Iterationen
            // nicht bei partnerA und partnerB die gleiche DNA auswählt.
            // Implementierung auch mit Sets möglich
            // Weitere Bedingungen bei der Generationserzeugung möglich z.b.:
            // afc.calculateFitness(child); && child.getFitness() <=
            // 1.5f*currentBestFitness
            if (!nextGeneration.contains(child))
            {
                nextGeneration.add(child);
            }
        }
        population.clear();
        population.addAll(nextGeneration);
        // berechnet die fitness aller DNA Objekte in population
        calculateFitness();
        // Sortiert nach der fitness (aufsteigend, je niedriger desto besser)
        Collections.sort(population);

        if (generations == 0 || population.get(0).getFitness() > currentBestFitness)
        {
            System.out.println("new best fitness: " + population.get(0).getFitness() + ", pathlength: "
                    + population.get(0).getPathLength() + " in iteration " + generations);
            //Print der cooling verteilung einer DNA

            ArrayList<Integer> cooling = new ArrayList<Integer>();
            for (Integer i : population.get(0).getTranslation())
            {
                int coolingInNode = 0;
                for (Article a : g.getNodeById(i).getArticles())
                {
                    if(a.getCooling() == 2)
                    {
                        coolingInNode+=5;
                    }
                    else if(a.getCooling() == 1)
                    {
                        coolingInNode+=1;
                    }
                }
                cooling.add(coolingInNode);
            }

            System.out.println(cooling);

            currentBestGeneration = generations + 1;
        }
        // Definier currentBestFitness als das beste Element der aktuellen
        // Generation (kann nie schlechter werden, da
        // das beste Ergebnis immer in die nächste Generation übernommen wird
        currentBestFitness = population.get(0).getFitness();
        // Zähle generations hoch (counter für die Anzahl an erzeugten
        // Generationen)
        generations++;
        if (generations % 100 == 0)
        {
            //System.out.println(population.get(0));
        }
    }

    public DNA bestResult()
    {
        return population.get(0);
    }

    public DNA secondBestResult()
    {
        for (DNA adna : population)
        {
            if (adna.getFitness() > currentBestFitness)
            {
                return adna;
            }
        }
        return null;
    }

    public ArrayList<DNA> getCurrentGeneration()
    {
        return population;
    }

    @Override
    public String toString()
    {
        return "Population{" +
                "mutationrate=" + mutationrate +
                ", populationSize=" + populationSize +
                ", elite=" + elite +
                ", eliteSize=" + eliteSize +
                ", mutationStrat=" + mutationStrat +
                ", combineStrat=" + combineStrat +
                ", fitnessStrat=" + fitnessStrat +
                ", terminationStrat=" + terminationStrat +
                ", selectionWeight=" + selectionWeight +
                '}';
    }
}
