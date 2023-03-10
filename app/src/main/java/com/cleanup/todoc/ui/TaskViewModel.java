package com.cleanup.todoc.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    // REPOSITORIES
    private final ProjectDataRepository projectDataSource;
    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<List<Project>> currentProjects;
    private LiveData<List<Task>> currentTasks;

    public TaskViewModel(ProjectDataRepository projectDataSource, TaskDataRepository taskDataSource, Executor executor) {
        this.projectDataSource = projectDataSource;
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    public void init() {
        if (this.currentProjects != null) {
            return;
        }
        currentProjects = projectDataSource.getProjects();
        currentTasks = taskDataSource.getTasks();
    }

    // PROJECTS :
    public LiveData<List<Project>> getProjects() {
        return this.currentProjects;
    }

    // TASKS CRUD :
    // C
    public void createTask(Task task) {
        executor.execute(() -> taskDataSource.createTask(task));
    }

    // R
    public LiveData<List<Task>> getTasks() { return this.currentTasks; }

    // U
    public void updateTask(Task task) {
        executor.execute(() -> taskDataSource.updateTask(task));
    }

    // D
    public void deleteTask(long taskId) {
        executor.execute(() -> taskDataSource.deleteTask(taskId));
    }
}
