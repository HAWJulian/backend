package com.example.shop.repositories;

import com.example.shop.entities.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long>{
    //ArrayList<Article> findByObjecctIdIn(int[] ids);
    @Query( "select o from Article o where objectId in :ids" )
    ArrayList<Article> findByObjectId(@Param("ids") ArrayList<Long> articleIds);

    ArrayList<Article> findArticlesByCooling(int cooling);
}
