package viewmodels;

import io.ebean.Finder;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;

public class AccountStatusViewModel{

    public String CurrentUser;
    public String Role;
    public Boolean IsLogged;
    public Boolean IsOwner;

    public AccountStatusViewModel(String mail, Boolean logged, Boolean IsOwner, String Role) {
        this.CurrentUser = mail;
        this.IsLogged = logged;
        this.IsOwner = IsOwner;
        this.Role = Role;
    }

    public void SetCurrentUser(String usermail){
        this.CurrentUser = usermail;
    }

    public String GetCurrentUser(){
        return this.CurrentUser;
    }

    public void SetRole(String Role){
        this.Role = Role;
    }

    public String GetRole(){
        return this.Role;
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