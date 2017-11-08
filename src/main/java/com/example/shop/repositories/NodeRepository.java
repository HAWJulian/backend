package com.example.shop.repositories;

import com.example.shop.entities.SupermarketNode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface NodeRepository extends CrudRepository<SupermarketNode, Long>
{
    @Query( "select o from SupermarketNode o where objectId in :ids" )
    ArrayList<SupermarketNode> findByObjectIds(@Param("ids") ArrayList<Long> nodeIds);
}
