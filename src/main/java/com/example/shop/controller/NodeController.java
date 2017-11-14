package com.example.shop.controller;

import com.example.shop.entities.Supermarket;
import com.example.shop.entities.SupermarketNode;
import com.example.shop.services.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin("*")
public class NodeController
{
    @Autowired
    NodeService nodeService;

    @GetMapping(value="/node/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public SupermarketNode findById(@PathVariable long id)
    {
        return nodeService.findById(id);
    }

    @GetMapping(value="/nodes/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<SupermarketNode> findAllNodesInShop(@PathVariable long id){ return nodeService.findAllNodesInSupermarket(id); }
    /*
    @GetMapping(value="/nodes/complete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<SupermarketNode> findAllNodesWithArticles (@PathVariable long id) {return nodeService.findAllNodesWithArticlesInSupermarket(id);}
    */
}
