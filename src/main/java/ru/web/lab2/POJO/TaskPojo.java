package ru.web.lab2.POJO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import ru.web.lab2.models.Task;

@Getter
@Setter
public class TaskPojo {
    private long id;
    private String title;
    private String description;
    private LocalDate date_end;
    private boolean is_done;

    public static TaskPojo fromEntity(Task task){
        TaskPojo pojo = new TaskPojo();
        pojo.setId(task.getId());
        pojo.setTitle(task.getTitle());
        pojo.setDescription(task.getDescription());
        pojo.setDate_end(task.getDate_end());
        pojo.set_done(task.is_done());
        return pojo;
    }

    public static Task toEntity(TaskPojo pojo){
        Task task = new Task();
        task.setId(pojo.getId());
        task.setTitle(pojo.getTitle());
        task.setDescription(pojo.getDescription());
        task.setDate_end(pojo.getDate_end());
        task.set_done(pojo.is_done());
        return task;
    }
}
