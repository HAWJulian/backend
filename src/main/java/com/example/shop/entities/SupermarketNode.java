package com.example.shop.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
@Table(name = "nodes")
public class SupermarketNode implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long objectId;

    @Column(name = "articleIds")
    private ArrayList<Long> articleIds;

    @Column(name = "neighbours")
    private ArrayList<Long> neighbourIds;
    //0 = noCooling, 1=Cooling (fridge), 2 = freezing (freezer), -1 Entry, -2 Exit
    @Column(name = "type")
    private int type;



    //Constructor
    protected SupermarketNode(){};

    public SupermarketNode(ArrayList<Long> articleIds, ArrayList<Long> neighbourIds, int type)
    {
        this.articleIds = articleIds;
        this.neighbourIds = neighbourIds;
        this.type = type;
    }

    //Getter & Setter
    public long getObjectId()
    {
        return objectId;
    }

    public void setObjectId(long objectId)
    {
        this.objectId = objectId;
    }

    public ArrayList<Long> getArticleIds()
    {
        return articleIds;
    }

    public void setArticleIds(ArrayList<Long> articleIds)
    {
        this.articleIds = articleIds;
    }

    public ArrayList<Long> getNeighbourIds()
    {
        return neighbourIds;
    }

    public void setNeighbourIds(ArrayList<Long> neighbourIds)
    {
        this.neighbourIds = neighbourIds;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }
    @Override
    public String toString()
    {
        return "SupermarketNode{" +
                "objectId=" + objectId +
                ", articleIds=" + articleIds +
                ", neighbourIds=" + neighbourIds + "(type:" + type + ")" +
                '}';
    }
}
