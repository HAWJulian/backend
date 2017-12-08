package com.example.shop.businesslogic.ga;

import com.example.shop.businesslogic.graphgen.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DNA implements Comparable<DNA>
{
    private Integer[] genes;
    private Integer[] translation;
    private float fitness;
    private int pathLength;
    //Bildet den Path der DNA auf den Wert cooling ab, in dem die Summe aller coolingWerte einer SupermarketNode gebildet und mit
    //dem index der SupermarketNode in translation multipliziert wird
    //Beispiel: 0,1,1,2,5,6 => 0*1 + 1*2 + 1*3 + 2*4 + 5*5 + 6*6
    //Die Werte ^ sind abhängig von der Implementierung des FitnessCalculators
    private int cooling;
    private ArrayList<Integer> coolingOrder;
    private float coolingPercent;
    private ArrayList<Integer> reducedIdOrder;
    private Random rdm;
    public DNA(int size, ArrayList<Integer> reducedIdOrder)
    {
        rdm = new Random();
        genes = new Integer[size];
        translation = new Integer[size];
        this.reducedIdOrder = reducedIdOrder;
    }
    public void randomize()
    {
        //Anfang und Ende jeweils 1
        genes[0] = 1;
        genes[genes.length-1] = 1;
        //Alle andere dazwischen: size-i (größtmöglicher Wert in Iteration i)
        //-1 damit der letzte Wert für genes[size-1] "reserviert bleibt" (Start node = 1, Endwert = size)
        for(int i = 1; i < genes.length-1; i++)
        {
            genes[i] = 1+rdm.nextInt(genes.length-1-i);
        }
        calculateTranslation();
    }
    //Berechnet die Reihenfolge in der die Nodes besucht werden in Abhängigkeit von den genes
    //genes -> translation
    public void calculateTranslation()
    {
        ArrayList<Integer> helper = new ArrayList<Integer>();
        for(int i = 0; i < genes.length; i++)
        {
            helper.add(reducedIdOrder.get(i));
        }

        for(int i = 0; i < genes.length; i++)
        {
            translation[i] = helper.get(genes[i]-1);
            helper.remove(genes[i]-1);
        }
    }
    //Berechnet aus der Reihenfolge in der die Nodes besucht werden die genes
    //translation -> genes
    public void calculateGenes()
    {
        ArrayList<Integer> helper = new ArrayList<Integer>();
        for(int i = 0; i < genes.length; i++)
        {
            helper.add(reducedIdOrder.get(i));
        }
        for(int i = 0; i < genes.length; i++)
        {
            genes[i] = 1+helper.indexOf(translation[i]);
            helper.remove(helper.indexOf(translation[i]));
        }
    }
    //Getter und Setter
    public Integer[] getGenes()
    {
        return genes;
    }
    public void setGenes(Integer[] genes) {this.genes = genes;}
    public float getFitness()
    {
        return fitness;
    }
    public void setFitness(float fitness)
    {
        this.fitness = fitness;
    }
    public Integer[] getTranslation()
    {
        return translation;
    }
    public void setTranslation(Integer[] translation){this.translation = translation;}
    public int getPathLength()
    {
        return pathLength;
    }
    public void setPathLength(int pathLength)
    {
        this.pathLength = pathLength;
    }
    public ArrayList<Integer> getReducedIdOrder()
    {
        return reducedIdOrder;
    }
    public ArrayList<Integer> getCoolingOrder()
    {
        return coolingOrder;
    }
    public void setCoolingOrder(ArrayList<Integer> coolingOrder)
    {
        this.coolingOrder = coolingOrder;
    }
    public int getCooling()
    {
        return cooling;
    }
    public void setCooling(int cooling)
    {
        this.cooling = cooling;
    }
    public float getCoolingPercent()
    {
        return coolingPercent;
    }
    public void setCoolingPercent(float coolingPercent)
    {

        this.coolingPercent = Math.round(coolingPercent*100)/(float)100;
    }

    //Alternative Implementierungsmöglichkeiten:
    //Mutation anders implementieren als durch Vertauschen zweier Elemente
    //Mutate auf Genes durch Zufallszahl
    //...
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof DNA))
        {
            return false;
        }
        DNA toCheck = (DNA) other;
        return Arrays.deepEquals(toCheck.getGenes(), this.getGenes());
    }
    @Override
    public String toString()
    {
        return "Genes: " + Arrays.toString(genes) + ", Fitness: " + fitness + ", pathlength: " + pathLength + ", Translation" + Arrays.toString(translation) + ", coolingorder: " + coolingOrder +
                "coolingPercent: " + coolingPercent + "cooling: " +  this.cooling;
    }
    //CompareTo für das Vergleichen zum Sortieren von DNA in Collections
    @Override
    public int compareTo(DNA other)
    {
        if(this.getFitness() > other.getFitness())
        {
            return -1;
        }
        else if(this.getFitness() == other.getFitness())
        {
            return 0;
        }
        return 1;
    }
}
