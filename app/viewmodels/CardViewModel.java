package viewmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardViewModel {


    @JsonProperty
    public int parentList;
    @JsonProperty
    public String name;
    @JsonProperty
    public String description;
    @JsonProperty
    public int row;
}
