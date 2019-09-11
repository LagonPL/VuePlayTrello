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
    public int teamId;
    @JsonProperty
    public String mail;

    public TeamViewModel(int teamId, String mail) {

        this.teamId = teamId;
        this.mail = mail;
    }

    public TeamViewModel() {

    }
}
