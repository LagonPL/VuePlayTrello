package models;

//import play.data.validation.Constraints;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.ebean.Finder;
import io.ebean.annotation.EnumValue;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Board extends BaseModel {


    public static Finder<Integer, Board> find = new Finder<Integer, Board>(Board.class);
    @Nullable
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentBoard")
    @JsonManagedReference
    public List<Listt> listts = new ArrayList<Listt>();
    @Nullable
    @ManyToOne
    @JsonBackReference
    User ownerUser;
    public String userList;
    Status status;
    boolean isPrivate;



    public Board() {
        this.status=Status.VISIBLE;
    }

    @Nullable
    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(@Nullable User ownerUser) {
        this.ownerUser = ownerUser;
    }

    @Nullable
    public List<Listt> getListts() {
        return listts;
    }

    public void setListts(@Nullable List<Listt> listts) {
        this.listts = listts;
    }

    @Nullable
    public String getUserList() {
        return userList;
    }

    public void setUserList(@Nullable String userList) {
        this.userList = userList;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
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
