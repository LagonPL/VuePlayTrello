package viewmodels;

import io.ebean.Finder;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;

public class AccountStatusViewModel{

    public String CurrentUser;
    public Boolean IsMember;
    public Boolean IsLogged;
    public Boolean IsOwner;

    public AccountStatusViewModel(String mail, Boolean logged, Boolean IsOwner, Boolean IsMember) {
        this.CurrentUser = mail;
        this.IsLogged = logged;
        this.IsOwner = IsOwner;
        this.IsMember = IsMember;
    }

    public void SetCurrentUser(String usermail){
        this.CurrentUser = usermail;
    }

    public String GetCurrentUser(){
        return this.CurrentUser;
    }

    public void SetIsMember(Boolean IsMember){
        this.IsMember = IsMember;
    }

    public Boolean GetIsMember(){
        return this.IsMember;
    }

    public void SetIsLogged(Boolean anylogged){
        this.IsLogged = anylogged;
    }

    public Boolean GetIsLogged(){
        return this.IsLogged;
    }

    public void SetIsOwner(Boolean IsOwner){
        this.IsLogged = IsOwner;
    }

    public Boolean GetIsOwner(){
        return this.IsOwner;
    }

}