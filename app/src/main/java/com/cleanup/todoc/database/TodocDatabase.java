package com.cleanup.todoc.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.Executors;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    // Singleton
    private  static volatile TodocDatabase INSTANCE;

    // DAO
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();

    // Instance:
    public static TodocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static TodocDatabase getMemoryInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class)
                    .addCallback(prepopulateDatabase())
                    .build();
        }

        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.projectDao().createProject(
                        new Project(1L, "Projet Tartampion", 0xFFEADAD1)
                ));
                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.projectDao().createProject(
                        new Project(2L, "Projet Lucidia", 0xFFB4CDBA)
                ));
                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.projectDao().createProject(
                        new Project(3L, "Projet Circus", 0xFFA3CED2)
                ));
            }
        };
    }

}
