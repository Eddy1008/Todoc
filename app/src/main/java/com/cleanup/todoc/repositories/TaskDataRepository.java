package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    // C
    public void createTask(Task task) {
        this.taskDao.insertTask(task);
    }

    // R
    public LiveData<List<Task>> getTasks() {
        return this.taskDao.getTasks();
    }

    // U
    public void updateTask(Task task) {
        this.taskDao.updateTask(task);
    }

    // D
    public void deleteTask(long taskId) {
        this.taskDao.deleteTask(taskId);
    }

    public LiveData<List<Task>> getTasksByProject(long projectId) {
        return this.taskDao.getTasksByProject(projectId);
    }
}
