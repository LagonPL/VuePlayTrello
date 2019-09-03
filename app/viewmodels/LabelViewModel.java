package viewmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LabelViewModel {

    @JsonProperty
    public String name;
    @JsonProperty
    public String color;
    @JsonProperty
    public int parentCard;
}
