package com.example.shop.businesslogic.graphgen;

import com.example.shop.businesslogic.ga.DNA;
import com.example.shop.entities.Article;
import com.example.shop.entities.SupermarketNode;

import java.util.ArrayList;
import java.util.Arrays;

public class GraphCalculator
{

    //Erstellt einen Graph aus den übergebenen supermarketNodes und fügt den Nodes die zu kaufenden Artikel hinzu
    public Graph calculateGraph(ArrayList<SupermarketNode> supermarketNodes, ArrayList<Article> articles)
    {
        int articlecount = 0;
        System.out.println("start graph calculation");
        ArrayList<Node> helperNodes = new ArrayList<>();
        for (SupermarketNode supermarketNode : supermarketNodes)
        {
            Node n = new Node();
            n.setObjectId((int) supermarketNode.getObjectId());
            ArrayList<Article> articlesInNode = new ArrayList<>();
            for (long l : supermarketNode.getArticleIds())
            {

                for (Article article : articles)
                {
                    if(article.getObjectId() == l)
                    {
                        articlesInNode.add(article);
                        articlecount++;
                    }
                }
            }
            n.setArticles(articlesInNode);
            helperNodes.add(n);
        }
        System.out.println("articles added: " + articlecount);
        Graph graphHelper = new Graph(helperNodes, new ArrayList<Edge>());
        System.out.println("ids print");
        for (Node helperNode : helperNodes)
        {
            System.out.print(helperNode.getObjectId()+",");
        }
        System.out.println();
        System.out.println(graphHelper);
        ArrayList<Edge> helperEdges = new ArrayList<>();
        for (SupermarketNode supermarketNode : supermarketNodes)
        {
            Node start = graphHelper.getNodeById((int) supermarketNode.getObjectId());
            for (long id : supermarketNode.getNeighbourIds())
            {
                Node end = graphHelper.getNodeById((int) id);
                Edge e = new Edge(start, end, 1);
                if(!helperEdges.contains(e) && !start.equals(end))
                {
                    helperEdges.add(e);
                }
            }
        }
        for (Edge helperEdge : helperEdges)
        {
            helperEdge.getStartNode().addEdge(helperEdge);
            helperEdge.getEndNode().addEdge(helperEdge);
        }
        
        //Neighbours, Edges
        Graph result = new Graph(helperNodes, helperEdges);

        System.out.println(result);
        return result;
    }

    public Graph reduceGraph(ArrayList<Node> nodes)
    {
        ArrayList<Edge> reducedEdges = new ArrayList<>();
        for (Node n : nodes)
        {
            n.resetEdges();
            n.resetNeighbours();
        }
        for (Node n : nodes)
        {
            for (Node neighbour : nodes)
            {
                if(!n.equals(neighbour))
                {
                    Edge neighbouredge = new Edge(n, neighbour, n.getShortestPathsLengths()[neighbour.getObjectId()]);
                    if(!reducedEdges.contains(neighbouredge))
                    {
                        n.addEdge(neighbouredge);
                        neighbour.addEdge(neighbouredge);
                        reducedEdges.add(neighbouredge);
                    }
                }
            }
        }

        return new Graph(nodes,reducedEdges);
    }
    public ArrayList<Integer> calculateTourOnOriginalGraph(Graph g, DNA d)
    {
        ArrayList<Integer> originalTour = new ArrayList<Integer>();
        originalTour.add(d.getTranslation()[0]);

        for(int i = 0; i < d.getTranslation().length-1; i++)
        {
            Node start = g.getNodeById(d.getTranslation()[i]);
            Node end =g.getNodeById(d.getTranslation()[i+1]);
            int index = start.getObjectId();

            while(index != end.getObjectId())
            {
                originalTour.add(end.getShortestPaths()[index]);
                index = end.getShortestPaths()[index];
            }
        }
        /*
        System.out.print("path in original graph: ");
        System.out.println(originalTour);
        */

        return originalTour;
    }

}
