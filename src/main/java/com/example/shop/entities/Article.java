package com.example.shop.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name= "articles")
public class Article implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;
    //DB
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long objectId;

    @Column(name = "name")
    private String articleName;

    //Preis in cent
    @Column(name = "price")
    private int price;

    @Column(name = "manufacturer")
    private String manufacturer;

    //War vorher enum > benötigt refactor in anderen Klassen (done?)
    @Column(name = "cooling")
    private int cooling;

    //Constructor
    protected Article() {
    }

    public Article(String articleName, int price, String manufacturer, int cooling) {
        this.articleName = articleName;
        this.price = price;
        this.manufacturer = manufacturer;
        this.cooling = cooling;
    }

    //Getter
    public long getObjectId() {
        return objectId;
    }

    public String getArticleName() {
        return articleName;
    }

    public int getPrice() {
        return price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getCooling() {
        return cooling;
    }
    //Setter

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setCooling(int cooling) {
        this.cooling = cooling;
    }

    //Equals, hashCode, toString

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        return objectId == article.objectId;
    }

    @Override
    public int hashCode()
    {
        return (int) (objectId ^ (objectId >>> 32));
    }

    @Override
    public String toString() {
        return "Article{" +
                "objectId=" + objectId +
                ", articleName='" + articleName + '\'' +
                ", price=" + price +
                ", manufacturer='" + manufacturer + '\'' +
                ", cooling=" + cooling +
                '}';
    }
}
