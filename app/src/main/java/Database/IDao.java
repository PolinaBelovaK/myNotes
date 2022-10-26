package Database;
/**
 * Basic sql commands for program. Select all, update note, update pin, delete note. Work w/ RoomDB.
 */

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import Models.Notes;

@Dao

public interface IDao {

    @Insert (onConflict = REPLACE)
    void insert (Notes notes);

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Notes> getAll();

    @Query("UPDATE notes SET title = :title, notes = :notes WHERE ID = :id")
    void update (int id, String title, String notes);

    @Query("UPDATE notes SET pinned = :pin WHERE ID = :id")
    void pin(int id, boolean pin);

    @Delete
    void delete(Notes notes);
}
