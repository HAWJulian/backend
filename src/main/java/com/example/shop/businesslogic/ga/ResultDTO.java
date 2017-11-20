package com.example.shop.businesslogic.ga;

import java.util.ArrayList;

public class ResultDTO
{
    private DNA dna;
    private ArrayList<Integer> path;
    private int generation;

    public ResultDTO(DNA dna, int generation)
    {
        this.dna = dna;
        this.generation = generation;
    }

    public DNA getDna()
    {
        return dna;
    }

    public void setDna(DNA dna)
    {
        this.dna = dna;
    }

    public ArrayList<Integer> getPath()
    {
        return path;
    }

    public void setPath(ArrayList<Integer> path)
    {
        this.path = path;
    }

    public int getGeneration()
    {
        return generation;
    }

    public void setGeneration(int generation)
    {
        this.generation = generation;
    }

    @Override
    public String toString()
    {
        return "ResultDTO{" +
                "dna=" + dna +
                ", path=" + path +
                ", generation=" + generation +
                '}';
    }
}
