package com.example.shop.businesslogic.strategies.replacement;

import com.example.shop.businesslogic.ga.DNA;

import java.util.ArrayList;

public abstract class ReplacementStrategy
{
    public abstract ArrayList<DNA> replace(ArrayList<DNA> parents, ArrayList<DNA> children);
}
