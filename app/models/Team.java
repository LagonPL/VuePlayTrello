package models;

//import javax.persistence.Entity;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.ArrayList;

@Entity
public class Team extends Model {


    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name=)
    public Integer id;

    @Constraints.Required
    public String name;

    @Nullable
    @ManyToMany
    @JoinTable(name="ORDER_ITEM",
            joinColumns=@JoinColumn(name="my_order",referencedColumnName = "order_number"),
            inverseJoinColumns = @JoinColumn(name="my_item", referencedColumnName="item_number"))
    public ArrayList<User> users;*/


}
