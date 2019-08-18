package models;


//import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.Finder;
import io.ebean.annotation.EnumValue;
import io.ebean.annotation.Length;
import play.data.format.Formats;

import javax.annotation.Nullable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Card extends BaseModel {

    public static Finder<Integer, Card> find = new Finder<Integer, Card>(Card.class);
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm")
    public Date dueDate = new Date();
    @Nullable
    @Length(512)
    String description;
    @ManyToOne
    @JsonBackReference
    Listt parentListt;
    int row;
    Status status;
    @Nullable
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentCard")
    @JsonManagedReference
    List<Comment> comments = new ArrayList<Comment>();
    @Nullable
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentCard")
    @JsonManagedReference
    List<Task> tasks = new ArrayList<Task>();

    public Card() {
        this.status = Status.VISIBLE;

    }

    public Card(String description, Listt parentListt, int row, String name) {
        this.description = description;
        this.parentListt = parentListt;
        this.row = row;
        this.name = name;
        this.status = Status.VISIBLE;
    }


    @Nullable
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(@Nullable List<Task> tasks) {
        this.tasks = tasks;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Nullable
    public String getDescription() {

        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addtComments(Comment comment) {
        this.comments.add(comment);
    }

    public Listt getParentListt() {
        return parentListt;
    }

    public void setParentListt(Listt parentListt) {
        this.parentListt = parentListt;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
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
