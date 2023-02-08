package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository {

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    // C
    public void createProject(Project project) {
        this.projectDao.createProject(project);
    }

    // R
    public LiveData<List<Project>> getProjects() {
        return this.projectDao.getProjects();
    }

    // U
    public void updateProject(Project project) {
        this.projectDao.updateProject(project);
    }

    // D
    public void deleteProject(long projectId) {
        this.projectDao.deleteProject(projectId);
    }

    public LiveData<Project> getProjectById(long projectId) {
        return this.projectDao.getProjectById(projectId);
    }
}
