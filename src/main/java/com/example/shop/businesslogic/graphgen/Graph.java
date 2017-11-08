package com.example.shop.businesslogic.graphgen;

import java.util.ArrayList;

public class Graph
{

    ArrayList<Node> nodes;
    ArrayList<Edge> edges;
    public Graph(ArrayList<Node> nodes, ArrayList<Edge> edges)
    {
        this.nodes = nodes;
        this.edges = edges;
    }


    public ArrayList<Node> getNodes()
    {
        return nodes;
    }
    public ArrayList<Edge> getEdges()
    {
        return edges;
    }
    public Node getNodeById(int id)
    {
        for (Node n : nodes)
        {
            if(n.getObjectId() == id)
            {
                return n;
            }
        }
        return null;
    }
    public Node getStartNode()
    {
        return this.getNodes().get(this.getNodes().size()-1);
    }
    public Node getEndNode()
    {
        return this.getNodes().get(this.getNodes().size()-2);
    }
    @Override
    public String toString()
    {
        String prep = "Nodes: ";
        for (Node n : nodes)
        {
            prep+=" " + n.toString() + ", ";
        }
        prep +=System.lineSeparator() + "Edges: ";
        for (Edge edge : edges)
        {
            prep+= " " + edge.toString() +", ";
        }
        return prep;
    }

}
