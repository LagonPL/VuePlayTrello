package viewmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskViewModel {

    @JsonProperty
    public int parentCard;
    @JsonProperty
    public String name;
}
