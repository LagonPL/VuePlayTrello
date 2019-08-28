package viewmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.Listt;
import models.User;

import javax.annotation.Nullable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;

public class UserViewModel {


    @JsonProperty
    public int parentBoard;
    @JsonProperty
    public String mail;

    public UserViewModel(int parentBoard, String mail) {

        this.parentBoard = parentBoard;
        this.mail = mail;
    }

    public UserViewModel() {

    }
}
