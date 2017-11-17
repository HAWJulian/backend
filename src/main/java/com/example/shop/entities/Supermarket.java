package com.example.shop.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
@Table(name = "supermarkets")
public class Supermarket implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nodes")
    private ArrayList<Long> nodeIds;
    /*
    @Column(name = "nodes")
    private node[][] nodes;
    */
    @Column(name = "dimension")
    private int rows;
    protected Supermarket()
    {
    }

    public Supermarket(ArrayList<Long> nodeIds, int rows)
    {
        this.nodeIds = nodeIds;
        this.rows = rows;
    }

    //Getter
    public ArrayList<Long> getNodeIds()
    {
        return nodeIds;
    }
    public int getDimension()
    {
        return this.rows;
    }
    //Setter
    public void setNodes(ArrayList<Long> nodeIds)
    {
        this.nodeIds = nodeIds;
    }
    public void setDimension(int rows)
    {
        this.rows = rows;
    }
}
