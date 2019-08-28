package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.Finder;

import javax.annotation.Nullable;
import javax.persistence.ManyToOne;
import javax.persistence.*;

@Entity
public class EventLog extends BaseModel {

    public static Finder<Integer, EventLog> find = new Finder<Integer, EventLog>(EventLog.class);
    String text;
    int boardId;

    public EventLog(int boardId, String text) {
        this.text = text;
        this.boardId = boardId;
    }

    public EventLog() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }
}
