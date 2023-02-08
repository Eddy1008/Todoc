package com.cleanup.todoc.ui;

import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * <p>Adapter which handles the list of tasks to display in the dedicated RecyclerView.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {
    /**
     * The list of tasks the adapter deals with
     */
    @NonNull
    private List<Task> tasks;

    /**
     * The list of projects the adapter deals with
     */
    @NonNull
    private Project[] projects;

    /**
     * The listener for when a task needs to be deleted
     */
    @NonNull
    private final DeleteTaskListener deleteTaskListener;

    /**
     * Instantiates a new TasksAdapter.
     *
     * @param tasks the list of tasks the adapter deals with to set
     */
    TasksAdapter(@NonNull final List<Task> tasks, @NonNull final Project[] projects, @NonNull final DeleteTaskListener deleteTaskListener) {
        this.tasks = tasks;
        this.projects = projects;
        this.deleteTaskListener = deleteTaskListener;
    }

    /**
     * Updates the list of tasks the adapter deals with.
     *
     * @param tasks the list of tasks the adapter deals with to set
     */
    void updateTasks(@NonNull final List<Task> tasks) {
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }

    /**
     * Updates the list of projects the adapter deals with.
     *
     * @param projects the list of projects the adapter deals with
     */
    void updateProjects(@NonNull final Project[] projects) {
        this.projects = projects;
        //this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(view, deleteTaskListener, projects);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        taskViewHolder.bind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    /**
     * Listener for deleting tasks
     */
    public interface DeleteTaskListener {
        /**
         * Called when a task needs to be deleted.
         *
         * @param task the task that needs to be deleted
         */
        void onDeleteTask(Task task);
    }

    /**
     * <p>ViewHolder for task items in the tasks list</p>
     *
     * @author Gaëtan HERFRAY
     */
    class TaskViewHolder extends RecyclerView.ViewHolder {
        /**
         * The circle icon showing the color of the project
         */
        private final AppCompatImageView imgProject;

        /**
         * The TextView displaying the name of the task
         */
        private final TextView lblTaskName;

        /**
         * The TextView displaying the name of the project
         */
        private final TextView lblProjectName;

        /**
         * The delete icon
         */
        private final AppCompatImageView imgDelete;

        /**
         * The listener for when a task needs to be deleted
         */
        private final DeleteTaskListener deleteTaskListener;


        /**
         * The list of project needed to build tasks
         */
        private final Project[] projects;

        /**
         * Instantiates a new TaskViewHolder.
         *  @param itemView the view of the task item
         * @param deleteTaskListener the listener for when a task needs to be deleted to set
         * @param projects The list of project needed to build tasks
         */
        TaskViewHolder(@NonNull View itemView, @NonNull DeleteTaskListener deleteTaskListener, Project[] projects) {
            super(itemView);

            this.deleteTaskListener = deleteTaskListener;
            this.projects = projects;

            imgProject = itemView.findViewById(R.id.img_project);
            lblTaskName = itemView.findViewById(R.id.lbl_task_name);
            lblProjectName = itemView.findViewById(R.id.lbl_project_name);
            imgDelete = itemView.findViewById(R.id.img_delete);


            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Object tag = view.getTag();
                    if (tag instanceof Task) {
                        TaskViewHolder.this.deleteTaskListener.onDeleteTask((Task) tag);
                    }
                }
            });
        }

        /**
         * Binds a task to the item view.
         *
         * @param task the task to bind in the item view
         */
        void bind(Task task) {
            lblTaskName.setText(task.getName());
            imgDelete.setTag(task);

            // final Project taskProject = task.getProject();
            final Project taskProject = getProjectById(task.getProjectId());

            if (taskProject != null) {
                imgProject.setSupportImageTintList(ColorStateList.valueOf(taskProject.getColor()));
                lblProjectName.setText(taskProject.getName());
            } else {
                imgProject.setVisibility(View.INVISIBLE);
                lblProjectName.setText("");
            }

        }

        private Project getProjectById(long id) {
            for (Project project : projects) {
                if (project.getId() == id) {
                    return project;
                }
            }
            return null;
        }
    }
}
