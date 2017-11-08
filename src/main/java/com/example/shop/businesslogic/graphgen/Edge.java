package com.example.shop.businesslogic.graphgen;

public class Edge
{
    private int weight;
    private Node startNode;
    private Node endNode;

    public Edge(Node startNode, Node endNode, int weight)
    {
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;
    }
    public boolean containsNode(Node node)
    {
        return this.getStartNode()==node || this.getEndNode()==node;
    }
    public boolean containsNodeId(int id)
    {
        return this.getStartNode().getObjectId()==id || this.getEndNode().getObjectId()==id;
    }
    public Node getOtherNode(Node node)
    {
        return this.getStartNode()==node ? this.getEndNode() : this.getStartNode();
    }

    // Getter und Setter
    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    public Node getStartNode()
    {
        return startNode;
    }

    public void setStartNode(Node startNode)
    {
        this.startNode = startNode;
    }

    public Node getEndNode()
    {
        return endNode;
    }

    public void setEndNode(Node endNode)
    {
        this.endNode = endNode;
    }
    // Override Methods
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Edge))
        {
            return false;
        }
        Edge check = (Edge) other;
        return (this.getStartNode() == check.getStartNode() && this.getEndNode() == check.getEndNode())
                || (this.getStartNode() == check.getEndNode() && this.getEndNode() == check.getStartNode());
    }
    @Override
    public String toString()
    {
        return this.getStartNode().getObjectId() + "-" + this.getEndNode().getObjectId() + " (" + this.getWeight() + ") ";
    }
}
