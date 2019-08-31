package viewmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.Listt;
import models.User;

import javax.annotation.Nullable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;

public class TeamViewModel {


    @JsonProperty
    public int parentTeam;
    @JsonProperty
    public String mail;
    @JsonProperty
    public String name;

    public TeamViewModel(int parentTeam, String mail, String name) {

        this.parentTeam = parentTeam;
        this.mail = mail;
        this.name = name;
    }

    public TeamViewModel() {

    }
}
