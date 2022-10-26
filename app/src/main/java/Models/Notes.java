package Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
/**
 * Create an entity table with the fields we need.
 * ID, title, notes(text), date and boolean var pinned (to pin a note).
 *
 */
@Entity (tableName = "notes")

public class Notes implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @ColumnInfo(name = "title")
    String title = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ColumnInfo(name = "notes")
    String notes = "";

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @ColumnInfo(name = "date")
    String date = "";

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @ColumnInfo(name = "pinned")
    boolean pinned = false;

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

}
