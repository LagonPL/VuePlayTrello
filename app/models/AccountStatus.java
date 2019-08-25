package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.Finder;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;

public class AccountStatus{

    public String CurrentUser;
    public Boolean IsLogged;

    public AccountStatus(String mail, Boolean logged) {
        this.CurrentUser = mail;
        this.IsLogged = logged;
    }

    public void SetCurrentUser(String usermail){
        this.CurrentUser = usermail;
    }

    public String GetCurrentUser(){
        return this.CurrentUser;
    }

    public void SetIsLogged(Boolean anylogged){
        this.IsLogged = anylogged;
    }

    public Boolean GetIsLogged(){
        return this.IsLogged;
    }

}