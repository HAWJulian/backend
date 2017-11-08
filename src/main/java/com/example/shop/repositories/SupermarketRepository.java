package com.example.shop.repositories;

import com.example.shop.entities.Supermarket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupermarketRepository extends CrudRepository<Supermarket, Long>{

}
