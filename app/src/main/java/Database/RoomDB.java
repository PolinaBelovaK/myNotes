package Database;
/**
 * DATABASE
 */

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import Models.Notes;

@Database(entities = Notes.class, version = 1, exportSchema = false)

public abstract class RoomDB extends RoomDatabase {

private static RoomDB db;
private static String DATABASE_NAME = "NoteApp";

public synchronized static RoomDB getInstance(Context context){
    if (db == null) {
        db = Room.databaseBuilder(context.getApplicationContext(),
                        RoomDB.class, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
    }
    return db;
}
public abstract IDao iDao();
}
