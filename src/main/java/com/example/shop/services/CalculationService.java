package com.example.shop.services;

import com.example.shop.businesslogic.ga.DNA;
import com.example.shop.businesslogic.ga.Population;
import com.example.shop.businesslogic.graphgen.DijkstraAlgorithm;
import com.example.shop.businesslogic.graphgen.Graph;
import com.example.shop.businesslogic.graphgen.GraphCalculator;
import com.example.shop.businesslogic.graphgen.Node;
import com.example.shop.businesslogic.shopping.ShoppingCart;
import com.example.shop.entities.Article;
import com.example.shop.entities.Supermarket;
import com.example.shop.entities.SupermarketNode;
import com.example.shop.repositories.ArticleRepository;
import com.example.shop.repositories.NodeRepository;
import com.example.shop.repositories.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class CalculationService
{
    @Autowired
    SupermarketRepository supermarketRepository;
    @Autowired
    NodeRepository nodeRepository;
    @Autowired
    ArticleRepository articleRepository;

    public ArrayList<Integer> startCalculation(Long supermarketId, ArrayList<Long> shoppingCart)
    {
        Supermarket s = supermarketRepository.findOne(supermarketId);
        System.out.println("Debug logs");
        System.out.println(supermarketId);
        System.out.println(shoppingCart);

        ArrayList<SupermarketNode> nodes = nodeRepository.findByObjectIds(s.getNodeIds());
        //Articles
        ArrayList<Article> articles = articleRepository.findByObjectId(shoppingCart);
        System.out.println("shoppingcart articles: ");
        for (Article article : articles)
        {
            System.out.println(article);
        }
        System.out.println("testing");
        SupermarketNode x = nodeRepository.findOne(176L);
        System.out.println("testing done");
        //Generate Graph
        GraphCalculator gc = new GraphCalculator();
        Graph g = gc.calculateGraph(nodes, articles);
        //Generate DistanceGraph (Dijkstra etc.)
        DijkstraAlgorithm da = new DijkstraAlgorithm(g, g.getNodes().get(0));
        for (Node n : g.getNodes())
        {
            da.setStartNode(n);
            da.execute();
        }

        for (Node node : g.getNodes())
        {
            System.out.print("("+ node.getObjectId() + ")");

            for(int i = 0; i < node.getShortestPaths().length; i++)
            {
                System.out.print(node.getShortestPaths()[i]);
                System.out.print(", ");
            }
            System.out.println();
            for(int i = 0; i < node.getShortestPathsLengths().length; i++)
            {
                System.out.print(node.getShortestPathsLengths()[i]);
                System.out.print(", ");
            }
            System.out.println();

        }
        //Generate ShoppingCart
        ShoppingCart sc = new ShoppingCart();
        sc.setCart(articles);
        sc.calculateCartNodes(g);
        System.out.println("shoppingcart: " + sc);
        Graph reducedGraph = gc.reduceGraph(sc.getCartNodes());
        System.out.println(reducedGraph);
        Population population = new Population(0.01f,reducedGraph.getNodes().size(),50,reducedGraph);
        DNA bestResult = population.execute();

        return gc.calculateTourOnOriginalGraph(g,bestResult);
    }
}
