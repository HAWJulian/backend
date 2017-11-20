package com.example.shop.services;

import com.example.shop.businesslogic.ga.DNA;
import com.example.shop.businesslogic.ga.Population;
import com.example.shop.businesslogic.ga.ResultDTO;
import com.example.shop.businesslogic.graphgen.DijkstraAlgorithm;
import com.example.shop.businesslogic.graphgen.Graph;
import com.example.shop.businesslogic.graphgen.GraphCalculator;
import com.example.shop.businesslogic.graphgen.Node;
import com.example.shop.businesslogic.shopping.ShoppingCart;
import com.example.shop.controller.SettingsDTO;
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

    public ArrayList<ResultDTO> startCalculation(Long supermarketId, ArrayList<Long> shoppingCart, SettingsDTO settings)
    {
        Supermarket s = supermarketRepository.findOne(supermarketId);
        System.out.println("Debug logs");
        System.out.println(supermarketId);
        System.out.println(shoppingCart);

        ArrayList<SupermarketNode> nodes = nodeRepository.findByObjectIds(s.getNodeIds());
        //Articles
        ArrayList<Article> articles = articleRepository.findByObjectId(shoppingCart);

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
        //Generate ShoppingCart
        ShoppingCart sc = new ShoppingCart();
        sc.setCart(articles);
        sc.calculateCartNodes(g);
        System.out.println("shoppingcart: " + sc);
        Graph reducedGraph = gc.reduceGraph(sc.getCartNodes());
        System.out.println(reducedGraph);
        //Population population = new Population(0.01f,reducedGraph.getNodes().size(),50,reducedGraph);
        Population population = new Population(reducedGraph.getNodes().size(),reducedGraph,settings);
        ArrayList<ResultDTO> results = population.execute();
        for (ResultDTO result : results)
        {
            result.setPath(gc.calculateTourOnOriginalGraph(g,result.getDna()));
            System.out.println(result);
        }
        return results;
        //return gc.calculateTourOnOriginalGraph(g,bestResult);
    }
    //Methode für editierte Graphen
    public ArrayList<ResultDTO> startEditedCalculation(ArrayList<SupermarketNode> nodes,ArrayList<Long> shoppingCart, SettingsDTO settings)
    {
        ArrayList<Article> articles = articleRepository.findByObjectId(shoppingCart);

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
        //Generate ShoppingCart
        ShoppingCart sc = new ShoppingCart();
        sc.setCart(articles);
        sc.calculateCartNodes(g);
        System.out.println("shoppingcart: " + sc);
        Graph reducedGraph = gc.reduceGraph(sc.getCartNodes());
        System.out.println(reducedGraph);
        //Population population = new Population(0.01f,reducedGraph.getNodes().size(),50,reducedGraph);
        Population population = new Population(reducedGraph.getNodes().size(),reducedGraph,settings);
        ArrayList<ResultDTO> results = population.execute();
        for (ResultDTO result : results)
        {
            result.setPath(gc.calculateTourOnOriginalGraph(g,result.getDna()));
            System.out.println(result);
        }
        return results;
    }
}
