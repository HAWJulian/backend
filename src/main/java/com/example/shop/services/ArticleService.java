package com.example.shop.services;

import com.example.shop.entities.Article;
import com.example.shop.entities.SupermarketNode;
import com.example.shop.entities.Supermarket;
import com.example.shop.repositories.ArticleRepository;
import com.example.shop.repositories.NodeRepository;
import com.example.shop.repositories.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    SupermarketRepository supermarketRepository;
    @Autowired
    NodeRepository nodeRepository;

    public List<Article> findAllInShop(Long shopId)
    {
        System.out.println("shopid: " + shopId);
        Supermarket s = supermarketRepository.findOne(shopId);
        ArrayList<Long> nodeIds = s.getNodeIds();
        ArrayList<Long> articleIds = new ArrayList<>();
        for (Long l: nodeIds)
        {
            SupermarketNode n = nodeRepository.findOne(l);
            for (Long artId: n.getArticleIds())
            {
                articleIds.add(artId);
            }
        }

        ArrayList<Article> ne = new ArrayList<Article>();

        //ne = articleRepository.findByObjecctIdIn(s.getArticleIds());
        ne = articleRepository.findByObjectId(articleIds);
        return ne;
        //return new ArrayList<Article>();
    }

}
