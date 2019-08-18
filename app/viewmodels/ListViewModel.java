package viewmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListViewModel {

    @JsonProperty
    public int parentBoard;
    @JsonProperty
    public String name;
    @JsonProperty
    public int column;

    public ListViewModel(int parentBoard, String name, int column) {

        this.parentBoard = parentBoard;
        this.name = name;
        this.column = column;
    }

    public ListViewModel() {

    }
}
