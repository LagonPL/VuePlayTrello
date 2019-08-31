package models;

//import javax.persistence.Entity;
import io.ebean.Model;
import play.data.validation.Constraints;
import io.ebean.Finder;
import io.ebean.Ebean;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.ArrayList;

@Entity
public class Team extends Model {

    public static Finder<Integer, Team> find = new Finder<Integer, Team>(Team.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(length = 256, unique = true, nullable = false)
    @Constraints.Required
    public String name;

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

    public static Team findByName(String name) {
        // todo: verify this query is correct.  Does it need an "and" statement?
        Team team = Ebean.find(Team.class).where().eq("name", name.toLowerCase()).findUnique();
        return team;
    }

}
