package com.cleanup.todoc.injection;

import android.content.Context;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

public class Di {

    public static boolean isForTesting = false;

    public static ProjectDataRepository getProjectRepository(Context context) {
        if (projectDataRepository == null) {
            if (isForTesting) {
                TodocDatabase database = TodocDatabase.getMemoryInstance(context);
                projectDataRepository = new ProjectDataRepository(database.projectDao());
            } else {
                TodocDatabase database = TodocDatabase.getInstance(context);
                projectDataRepository = new ProjectDataRepository(database.projectDao());
            }
        }
        return projectDataRepository;
    }

    public static TaskDataRepository getTaskRepository(Context context) {
        if (taskDataRepository == null) {
            if (isForTesting) {
                TodocDatabase database = TodocDatabase.getMemoryInstance(context);
                taskDataRepository = new TaskDataRepository(database.taskDao());
            } else {
                TodocDatabase database = TodocDatabase.getInstance(context);
                taskDataRepository = new TaskDataRepository(database.taskDao());
            }
        }
        return taskDataRepository;
    }

    static ProjectDataRepository projectDataRepository = null;
    static TaskDataRepository taskDataRepository = null;

}
