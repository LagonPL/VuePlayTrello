package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.Finder;

import javax.annotation.Nullable;
import javax.persistence.ManyToOne;
import javax.persistence.*;

@Entity
public class Comment extends BaseModel {

    public static Finder<Integer, Comment> find = new Finder<Integer, Comment>(Comment.class);
    @Nullable
    User ownerUser;
    String text;
    String userMail;
    @ManyToOne
    @JsonBackReference
    Card parentCard;

    public Comment(User ownerUser, String text, Card parentCard, String name) {
        this.ownerUser = ownerUser;
        this.text = text;
        this.parentCard = parentCard;
        this.userMail = ownerUser.getEmailAddress();
        this.name = name;
    }

    public Comment() {

    }

    @Nullable
    public User getOwnerUser() {

        return ownerUser;
    }

    public void setOwnerUser(@Nullable User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public String getUserMail() {

        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Card getParentCard() {
        return parentCard;
    }

    public void setParentCard(Card parentCard) {
        this.parentCard = parentCard;
    }
}
