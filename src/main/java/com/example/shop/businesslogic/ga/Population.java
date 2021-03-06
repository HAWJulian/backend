package com.example.shop.businesslogic.ga;

import com.example.shop.businesslogic.graphgen.Graph;
import com.example.shop.businesslogic.graphgen.Node;
import com.example.shop.businesslogic.strategies.combine.BasicCombine;
import com.example.shop.businesslogic.strategies.combine.CombineStrategy;
import com.example.shop.businesslogic.strategies.combine.OxCombine;
import com.example.shop.businesslogic.strategies.combine.UniformCombine;
import com.example.shop.businesslogic.strategies.fitnesscalculator.AbstractFitnessCalculator;
import com.example.shop.businesslogic.strategies.fitnesscalculator.AdvancedFitnessCalculator;
import com.example.shop.businesslogic.strategies.fitnesscalculator.BasicFitnessCalculator;
import com.example.shop.businesslogic.strategies.fitnesscalculator.GenerationInternFitnessCalculator;
import com.example.shop.businesslogic.strategies.mutate.AdvancedMutation;
import com.example.shop.businesslogic.strategies.mutate.BasicMutation;
import com.example.shop.businesslogic.strategies.mutate.MutateStrategy;
import com.example.shop.businesslogic.strategies.mutate.RandomSwapMutation;
import com.example.shop.businesslogic.strategies.replacement.BestIndividualsReplacement;
import com.example.shop.businesslogic.strategies.replacement.ChildrenReplacement;
import com.example.shop.businesslogic.strategies.replacement.RandomReplacement;
import com.example.shop.businesslogic.strategies.replacement.ReplacementStrategy;
import com.example.shop.businesslogic.strategies.termination.*;
import com.example.shop.controller.SettingsDTO;
import com.example.shop.entities.Article;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population
{
    private ArrayList<DNA> inResults;
    float mutationrate;
    int currentBestGeneration = 0;
    ArrayList<DNA> population;
    ArrayList<DNA> matingPool;
    int generations;
    int populationSize;
    Graph g;
    float currentBestFitness;
    DNA currentBestDNA;
    Random rdm;
    AbstractFitnessCalculator afc;
    CombineStrategy cs;
    MutateStrategy ms;
    TerminationStrategy ts;
    ReplacementStrategy rs;
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
    int replacementStrat = 0;
    float selectionWeight = 1f;
    private ArrayList<ResultDTO> results;

    //public Population(float mutationrate, int amtNodes, int popsize, Graph g)
    public Population(int amtNodes, Graph g, SettingsDTO settingsDTO)
    {
        System.out.println("started population");
        results = new ArrayList<ResultDTO>();
        inResults = new ArrayList<>();
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
        this.replacementStrat = settingsDTO.getReplacementStrat();
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
            case 2:
                cs = new UniformCombine();
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
            case 2:
                ms = new RandomSwapMutation();
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
            case 2:
                afc = new GenerationInternFitnessCalculator(g);
                break;
            default:
                afc = new BasicFitnessCalculator(g);
                break;
        }
        afc.setup(settingsDTO.getFitnessParam());
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
        switch (replacementStrat)
        {
            case 0:
                rs = new ChildrenReplacement();
                break;
            case 1:
                rs = new BestIndividualsReplacement();
                break;
            case 2:
                rs = new RandomReplacement();
                break;
            default:
                rs = new ChildrenReplacement();
                break;
        }
        while (population.size() < populationSize)
        {
            DNA toAdd = new DNA(amtNodes, reducedIdOrder);
            toAdd.randomize();
            if(!population.contains(toAdd))
            {
                population.add(toAdd);
            }
        }
        // Berechnet die Fitness aller DNA in population
        calculateFitness();
        // Sortiert die Population nach der Fitness (aufsteigend)
        Collections.sort(population);
        currentBestFitness = population.get(0).getFitness();
        currentBestDNA = population.get(0);
        // System.out.println(currentBestFitness);
        // generations - currentBestGeneration < 400
        //TODO fitness scaling?
        //TODO Duplcates?
        //TODO strategies
        //TODO selection strategy impl?

    }

    private void calculateFitness()
    {
        afc.evaluateGeneration(population);
        for (DNA dna : population)
        {
            afc.calculateFitness(dna);
        }
    }
    private void calcFitnessChildren(ArrayList<DNA> children)
    {
        for (DNA child : children)
        {
            afc.calculatePathlength(child);
            afc.calculateCooling(child);
            afc.calculateFitness(child);
            //System.out.println("child: " + child);
        }
    }
    public ArrayList<ResultDTO> execute()
    {
        while (!ts.checkTermination())
        {
            generateNewGeneration();
            ts.update(population);
        }

        System.out.println("best result: " + currentBestFitness + " in " + generations + " iterations");
        System.out.println(population.get(0));
        System.out.println("total amt generations " + generations);
        return results;
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
            float relativeFitness = remap(population.get(i).getFitness(), population.get(0).getFitness(),
                    population.get(populationSize - 1).getFitness(), 1, 0);
            //System.out.println("relative fitness: "+ relativeFitness);
            // System.out.println("relativeFitness: " + relativeFitness);
            long n = Math.round(Math.pow(relativeFitness, selectionWeight) * 100);
            for (int j = 0; j < n+1; j++)
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
        //TODO Elite nur sinnvoll bei children replacement!
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
        //population.clear();
        //population.addAll(nextGeneration);

        calcFitnessChildren(nextGeneration);
        population = rs.replace(population,nextGeneration);
        // berechnet die fitness aller DNA Objekte in population
        calculateFitness();
        // Sortiert nach der fitness (aufsteigend, je niedriger desto besser)
        Collections.sort(population);

        //results.add(new ResultDTO(population.get(0), generations));
        //System.out.println("current best: " + currentBestDNA);
        //System.out.println("population 0 : "+population.get(0));
        if (generations == 0 || population.get(0).getFitness() > afc.calculateFitness(currentBestDNA))
        {

            System.out.println("new best fitness: " + population.get(0).getFitness() + ", pathlength: "
                    + population.get(0).getPathLength() + "cooling: " + population.get(0).getCooling() + " in iteration " + generations);
            //Print der cooling verteilung einer DNA
            if(!inResults.contains(population.get(0)))
            {
                results.add(new ResultDTO(population.get(0), generations));
                inResults.add(population.get(0));
                ts.improvement(population.get(0));
                currentBestGeneration = generations + 1;
                // Definier currentBestFitness als das beste Element der aktuellen
                currentBestFitness = population.get(0).getFitness();
                currentBestDNA = population.get(0);
            }



        }

        // Zähle generations hoch (counter für die Anzahl an erzeugten
        // Generationen)
        generations++;
        if (generations % 100 == 0)
        {
            //System.out.println(population.get(0));
        }
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
