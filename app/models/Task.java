package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.Finder;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
public class Task extends BaseModel {

    public static Finder<Integer, Task> find = new Finder<Integer, Task>(Task.class);
    @ManyToOne
    @JsonBackReference
    Card parentCard;
    boolean done;

    public Task() {
        this.done = false;
    }
    public Task(Card parentCard, String name){
        this.parentCard = parentCard;
        this.done = false;
        this.name = name;
    }

    public Card getParentCard() {

        return parentCard;
    }

    public void setParentCard(Card parentCard) {
        this.parentCard = parentCard;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
