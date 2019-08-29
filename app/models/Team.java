package models;

//import javax.persistence.Entity;
import io.ebean.Model;
import play.data.validation.Constraints;
import io.ebean.Finder;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.ArrayList;

@Entity
public class Team extends BaseModel {

    public static Finder<Integer, Team> find = new Finder<Integer, Team>(Team.class);

    public int ownerUserId;
    public String userList;




    @Nullable
    public int getOwnerUser() {
        return ownerUserId;
    }

    public void setOwnerUser(@Nullable int ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    @Nullable
    public String getUserList() {
        return userList;
    }

    public void setUserList(@Nullable String userList) {
        this.userList = userList;
    }


}
