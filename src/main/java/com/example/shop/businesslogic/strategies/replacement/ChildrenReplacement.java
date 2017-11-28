package com.example.shop.businesslogic.strategies.replacement;

import com.example.shop.businesslogic.ga.DNA;

import java.util.ArrayList;

public class ChildrenReplacement extends ReplacementStrategy
{
    @Override
    public ArrayList<DNA> replace(ArrayList<DNA> parents, ArrayList<DNA> children)
    {
        return children;
    }
}
