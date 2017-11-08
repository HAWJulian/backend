package com.example.shop.services;

import com.example.shop.entities.Supermarket;
import com.example.shop.repositories.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupermarketService
{
    @Autowired
    SupermarketRepository supermarketRepository;

    public Supermarket findById(Long id)
    {
        return supermarketRepository.findOne(id);
    }

}
