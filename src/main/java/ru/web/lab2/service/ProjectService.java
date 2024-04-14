package ru.web.lab2.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import ru.web.lab2.POJO.*;
import ru.web.lab2.models.Project;
import ru.web.lab2.models.Task;
import lombok.RequiredArgsConstructor;
import ru.web.lab2.repositories.ProjectRepository;
import ru.web.lab2.repositories.TaskRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

   // public TaskPojo create(long projectId, TaskPojo pojo){
   //     Task task = TaskPojo.toEntity(pojo);
   //     task.setProject(projectRepository.findById(projectId).orElseThrow());
   //     return TaskPojo.fromEntity(taskRepository.save(task));
   // }

   //@Secured({"ADMIN"})
    public Set<ProjectPojo> getProjectsByText(String search){
        List<ProjectPojo> resultTitle = new ArrayList<>();
        for (Project project: search == " " ? projectRepository.findAll() : projectRepository.findAllByDescriptionContainingIgnoreCaseOrTitleContainingIgnoreCase(search, search)){
            resultTitle.add(ProjectPojo.fromEntity(project));
        }
        Set<ProjectPojo> setTitle = new HashSet<>(resultTitle);
        return setTitle;
    }

    //@Secured({"ADMIN"})
    public ProjectPojo getProjectById(long id){
        Optional<Project> answer = projectRepository.findById(id);
        if (answer.isEmpty())
            return null;
        Project project = answer.get();
        return ProjectPojo.fromEntity(project);
    }

    //@Secured({"ADMIN"})
    public long postProject(ProjectPojo pojo){
        Project entity = ProjectPojo.toEntity(pojo);
        entity = projectRepository.save(entity);
        return entity.getId();
    }

    //@Secured({"ADMIN"})
    public ProjectPojo putProject(long id, ProjectPojo pojo){
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()){
            Project project = projectOptional.get();
            Project entity = ProjectPojo.toEntity(pojo);
            entity.setId(id);
            entity.setTasks(project.getTasks());
            if (project != null){
                projectRepository.save(entity);
                return ProjectPojo.fromEntity(entity);
            }
        }
        return null;
    }

    //@Secured({"ADMIN"})
    public void deleteProject(long id){
        Optional<Project> answer = projectRepository.findById(id);
        if (answer.isPresent()){
            Project project = answer.get();
            projectRepository.delete(project); 
        }
    }

    //@Secured({"ADMIN"})
    public HashMap<Integer, Integer> getInfo(){
        HashMap<Integer, Integer> answer = new HashMap<>();
        List<Project> list = projectRepository.findAll();
        int temp = 0;
        for (Project project: list){
            List<Task> tasksList = project.getTasks();
            for (Task task: tasksList){
                if (!task.is_done()){
                    temp++;
                }
            }
            answer.put((int)project.getId(), temp);
            temp = 0;
        }
        return answer;
    }

    public List<TaskPojo> getAllTasks(long projectId){
        Optional<Project> answer = projectRepository.findById(projectId);
        if (answer.isPresent()){
            Project project = answer.get();
            List<TaskPojo> list = new ArrayList<>();
            for(Task task: project.getTasks()){
                TaskPojo pojo = TaskPojo.fromEntity(task);
                list.add(pojo);
            }
            return list;
        }
        return null;
    }

    public TaskPojo getTaskById(long projectId, long taskId){
        Optional<Task> answer = taskRepository.findById(taskId);
        if (answer.isEmpty())
            return null;
        Task task = answer.get();
        if (task.getProject().getId() == projectId)
            return TaskPojo.fromEntity(task);
        return null;
    }

    public TaskPojo postTask(long projectId, TaskPojo pojo){
        Task task = TaskPojo.toEntity(pojo);
        task.setProject(projectRepository.findById(projectId).orElseThrow());
        return TaskPojo.fromEntity(taskRepository.save(task));
    }

    public TaskPojo putTask(long projectId, long taskId, TaskPojo pojo){
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        Project project = optionalProject.get();
        if (project != null){
            Task entity = TaskPojo.toEntity(pojo);
            Task task = taskRepository.findByIdAndProjectId(taskId, projectId).get();
            entity.setProject(project);
            entity.setId(taskId);
            if (task != null) {
                taskRepository.save(entity);
                return TaskPojo.fromEntity(entity);
            }
        }
        return null;
    }

    public void deleteTask(long projectId, long taskId){
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        Project project = optionalProject.get();
        if (project != null){
            List<Task> list = project.getTasks();
            Optional<Task> answer = taskRepository.findById(taskId);
            if (answer.isPresent() && list.contains(answer.get())){
                taskRepository.deleteById(taskId); 
            }
        }
    }

    public void deleteDoneTasks(long projectId){
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        Project project = optionalProject.get();
        if (project != null){
            List<Task> list = project.getTasks();
            for (Task task : list){
                if (task.is_done() == true)
                    taskRepository.delete(task);
            }
        }
    }
}
