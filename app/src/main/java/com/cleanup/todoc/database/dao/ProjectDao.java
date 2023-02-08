package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    // C
    @Insert
    void createProject(Project project);

    // R
    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getProjects();

    // U
    @Update
    void updateProject(Project project);

    // D
    @Query("DELETE FROM Project WHERE id = :projectId")
    void deleteProject(long projectId);


    @Query("SELECT * FROM Project WHERE id = :projectId")
    LiveData<Project> getProjectById(long projectId);

}
