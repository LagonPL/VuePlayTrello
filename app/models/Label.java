package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.Finder;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
public class Label extends BaseModel {

    public static Finder<Integer, Label> find = new Finder<Integer, Label>(Label.class);
    @ManyToOne
    @JsonBackReference
    Card parentCard;
    String color;

    public Label(Card parentCard, String name, String color){
        this.parentCard = parentCard;
        this.name = name;
        this.color = color;
    }


}
