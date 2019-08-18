package viewmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.Listt;
import models.User;

import javax.annotation.Nullable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;

public class BoardViewModel {


    @JsonProperty
    public String name;
    @JsonProperty
    public int ownerUser;
    @JsonProperty
    public Boolean isPrivate=false;
}
