package com.example.shop.controller;

import com.example.shop.entities.Article;
import com.example.shop.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ArticleController {

    @Autowired
    ArticleService service;

    @GetMapping(value="/articles/{id}",  produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Article> findAllArticlesInShop(@PathVariable Long id)
    {
        List<Article> articles = service.findAllInShop(id);
        return articles;
    }
}
