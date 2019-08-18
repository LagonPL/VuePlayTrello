package models;

//import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.Finder;
import io.ebean.annotation.EnumValue;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNullableByDefault;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Listt extends BaseModel {


    public static Finder<Integer, Listt> find = new Finder<Integer, Listt>(Listt.class);
    @ManyToOne
    @JsonBackReference
    Board parentBoard;
    @Nullable
    @OneToMany(cascade = CascadeType.ALL, mappedBy="parentListt")
    @JsonManagedReference
    List<Card> cards = new ArrayList<Card>();
    int column;
    Status status;

    public Listt(Board parentBoard, Integer column, String Name) {
        this.parentBoard = parentBoard;
        if(column!=null) {
            this.column = column;
        }
        this.name = Name;
        this.status = Status.VISIBLE;
    }


    public Listt() {
        this.status = Status.VISIBLE;
    }

    public Board getParentBoard() {
        return parentBoard;
    }

    public void setParentBoard(Board parentBoard) {
        this.parentBoard = parentBoard;
    }

    @Nullable
    public List<Card> getCards() {
        return cards;
    }

    public void setCards(@Nullable List<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;

    }


    public enum Status {
        @EnumValue("V")
        VISIBLE,
        @EnumValue("A")
        ARCHIVED,
        @EnumValue("D")
        DELETED
    }

}
