package com.example.shop.controller;

import com.example.shop.entities.Supermarket;
import com.example.shop.services.SupermarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class SupermarketController {

    @Autowired
    SupermarketService supermarketService;

    @GetMapping(value="/supermarket/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public Supermarket findById(@PathVariable long id)
    {
        return supermarketService.findById(id);
    }
}
