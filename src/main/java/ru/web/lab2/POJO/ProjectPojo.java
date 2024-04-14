package ru.web.lab2.POJO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ru.web.lab2.models.Project;
import ru.web.lab2.models.Task;

@Getter
@Setter
public class ProjectPojo {
    private long id;
    private String title;
    private String description;
    private LocalDate date_start;
    private LocalDate date_end;
    private List<TaskPojo> tasks;

    public static ProjectPojo fromEntity(Project project){
        ProjectPojo pojo = new ProjectPojo();
        pojo.setId(project.getId());
        pojo.setTitle(project.getTitle());
        pojo.setDescription(project.getDescription());
        pojo.setDate_start(project.getDate_start());
        pojo.setDate_end(project.getDate_end());
        List<TaskPojo> tasks = new ArrayList<>();
        pojo.setTasks(tasks);
        for (Task task: project.getTasks()){
            tasks.add(TaskPojo.fromEntity(task));
        }
        return pojo;
    }

    public static Project toEntity(ProjectPojo pojo){
        Project project = new Project();
        project.setId(pojo.getId());
        project.setTitle(pojo.getTitle());
        project.setDescription(pojo.getDescription());
        project.setDate_start(pojo.getDate_start());
        project.setDate_end(pojo.getDate_end());
        List<Task> tasks = new ArrayList<>();
        project.setTasks(tasks);
        return project;
    }
}
