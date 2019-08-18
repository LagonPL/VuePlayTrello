package viewmodels;


import com.fasterxml.jackson.annotation.JsonProperty;
import models.Card;
import models.User;



public class CommentViewModel {


    @JsonProperty
    public int ownerUser;
    @JsonProperty
    public String text;
    @JsonProperty
    public int parentCard;
    @JsonProperty
    public String name;
}
