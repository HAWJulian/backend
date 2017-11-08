package com.example.shop.businesslogic.graphgen;

import com.example.shop.entities.Article;

import java.util.ArrayList;

public class Node
{
    private static int counter = 0;
    private int objectId;
    // Hält alle Kanten, ausgehend von der aktuellen Node
    private ArrayList<Edge> edges;
    // Hält alle Nachbarknoten der aktuellen Node
    private ArrayList<Node> neighbours;
    // Hält alle Artike, die in der aktuellen Node zu erhalten sind
    private ArrayList<Article> articles;
    // Hält die kürzesten Pfade von dieser Node zu jeder anderen Node des
    // ursprünglichen Graphen (Supermarkt), falls diese Node besucht werden mus
    private int[] shortestPaths;
    // Hält die Längen der kürzesten Pfade von dieser Node zu jeder anderen zu
    // besuchenden node, falls diese Node besucht werden muss
    private int[] shortestPathsLengths;

    public Node()
    {
        objectId = counter++;
        edges = new ArrayList<Edge>();
        neighbours = new ArrayList<Node>();
        articles = new ArrayList<Article>();
    }

    // Fügt eine Kante hinzu
    public void addEdge(Edge toAdd)
    {
        edges.add(toAdd);
        if (this.objectId == toAdd.getStartNode().getObjectId())
        {
            neighbours.add(toAdd.getEndNode());
        }
        else if (this.objectId == toAdd.getEndNode().getObjectId())
        {
            neighbours.add(toAdd.getStartNode());
        }
    }

    // Getter und Setter
    public ArrayList<Article> getArticles()
    {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles)
    {
        this.articles = articles;
    }

    public void setShortestPaths(int[] paths)
    {
        this.shortestPaths = paths;
    }

    public int[] getShortestPaths()
    {
        return this.shortestPaths;
    }

    public int[] getShortestPathsLengths()
    {
        return shortestPathsLengths;
    }

    public void setShortestPathsLengths(int[] shortestPathsLengths)
    {
        this.shortestPathsLengths = shortestPathsLengths;
    }

    public ArrayList<Edge> getEdges()
    {
        return edges;
    }

    public ArrayList<Node> getNeightbours()
    {
        return neighbours;
    }

    public int getObjectId()
    {
        return objectId;
    }
    public void setObjectId(int objectId)
    {
        this.objectId = objectId;
    }
    public void resetNeighbours()
    {
        neighbours.clear();
    }
    public void resetEdges()
    {
        edges.clear();
    }
    public void resetArticles()
    {
        articles.clear();
    }
    // Override Methods
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Node))
        {
            return false;
        }
        Node check = (Node) other;
        return this.objectId == check.objectId;
    }

    @Override
    public String toString()
    {
        String tostring = "Node: " + this.getObjectId() + " Artikel: ";
        for (Article article : articles)
        {
            tostring += article;
        }
        tostring += " Neighbours: ";
        for (Node n : this.getNeightbours())
        {
            tostring += n.getObjectId() + ", ";
        }

        return tostring + " ";
    }
}
