package com.example.shop.businesslogic.shopping;

import com.example.shop.businesslogic.graphgen.Graph;
import com.example.shop.businesslogic.graphgen.Node;
import com.example.shop.entities.Article;

import java.util.ArrayList;

public class ShoppingCart
{
    //Artikel die gekauft werden
    private ArrayList<Article> cart;
    //Daraus resultierende Nodes die im Supermarkt besucht werden müssen
    private ArrayList<Node> cartNodes;
    public ShoppingCart()
    {
        cart = new ArrayList<Article>();
        cartNodes = new ArrayList<Node>();
    }
    /*
    public void calculateCartNodes(Graph g)
    {
        cartNodes.clear();
        for (Node n : g.getNodes())
        {
            if(n.getArticles().size() > 0)
            {
                cartNodes.add(n);
            }
        }
    }
    */
    public void calculateCartNodes(Graph g)
    {

        for (int i = 0; i < g.getNodes().size(); i++)
        {
            Node toAdd = new Node();
            Node check = g.getNodes().get(i);
            toAdd.setObjectId(check.getObjectId());
            toAdd.setShortestPaths(check.getShortestPaths());
            toAdd.setShortestPathsLengths(check.getShortestPathsLengths());
            toAdd.resetArticles();
            System.out.println(check.getArticles());
            ArrayList<Article> articlesInNode = new ArrayList<Article>();
            for (Article a : cart)
            {
                if(check.getArticles().contains(a))
                {
                    articlesInNode.add(a);
                }


            }
            if(g.getNodes().get(i).getObjectId() == 177 || g.getNodes().get(i).getObjectId() == 178 || articlesInNode.size() > 0)
            {
                toAdd.setArticles(articlesInNode);
                cartNodes.add(toAdd);
            }

        }


        for (Node node : cartNodes)
        {
            System.out.println(node);
        }

    }

    public ArrayList<Article> getCart()
    {
        return cart;
    }
    public void setCart(ArrayList<Article> cart)
    {
        this.cart = cart;
    }
    public ArrayList<Node> getCartNodes()
    {
        return cartNodes;
    }
    public void setCartNodes(ArrayList<Node> cartNodes)
    {
        this.cartNodes = cartNodes;
    }
    /*
    @Override
    public String toString()
    {
        if(cartNodes == null)
        {
            return "Object empty";
        }

        String prepare= "";
        for (int i = 0; i<cartNodes.size(); i++)
        {
            prepare+=("ObjectID: " + cartNodes.get(i).getObjectId());
            prepare+=("Articles: ");
            for (Article article : cart)
            {

            }
            prepare+= System.lineSeparator();
        }
        return prepare;


    }
    */

    @Override
    public String toString()
    {
        return "ShoppingCart{Amtarticle: " + cart.size() + "amtnodes: " + cartNodes.size() +
                "cart=" + cart +
                ", cartNodes=" + cartNodes +
                '}';
    }
}
