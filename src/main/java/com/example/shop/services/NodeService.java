package com.example.shop.services;

import com.example.shop.entities.Supermarket;
import com.example.shop.entities.SupermarketNode;
import com.example.shop.repositories.NodeRepository;
import com.example.shop.repositories.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class NodeService
{
    @Autowired
    NodeRepository nodeRepository;
    @Autowired
    SupermarketRepository supermarketRepository;

    public SupermarketNode findById(long id)
    {
        return nodeRepository.findOne(id);
    }
    public ArrayList<SupermarketNode> findByObjectIds(ArrayList<Long> nodeIds)
    {
        return nodeRepository.findByObjectIds(nodeIds);
    }
    public ArrayList<SupermarketNode> findAllNodesInSupermarket(long supermarketId)
    {
        ArrayList<Long> nodeIdsInSupermarket = supermarketRepository.findOne(supermarketId).getNodeIds();
        return nodeRepository.findByObjectIds(nodeIdsInSupermarket);
    }
    /*
    public ArrayList<SupermarketNode> findAllNodesWithArticlesInSupermarket (long supermarketId)
    {
        //ArrayList<>
        return new ArrayList<SupermarketNode>();
    }
    */
}
