package ru.web.lab2.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import ru.web.lab2.POJO.ProjectPojo;
import ru.web.lab2.POJO.TaskPojo;
import ru.web.lab2.models.Project;
import ru.web.lab2.service.ProjectService;


@RestController
@RequestMapping("/api")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

 //   @GetMapping("/projects")
 //   public List<ProjectPojo> findAll(){
 //       return projectService.getProjectsByText(null);
 //  }
  //  @GetMapping("/projects/{title}")
  //  public List<ProjectPojo> findAllByName(@PathVariable String title){
  //      return projectService.findAll(title);
  //  }
  //  @PostMapping("/projects/{projectId}/tasks")
  //  public TaskPojo createTask(@PathVariable long projectId, @RequestBody TaskPojo pojo){
  //      return projectService.create(projectId, pojo);
  //  }

    @GetMapping("/admin/projects")
    public ResponseEntity<Set<ProjectPojo>> getProjectsByText(@RequestParam("search") String search){
        Set<ProjectPojo> answer = projectService.getProjectsByText(search);
        return new ResponseEntity<Set<ProjectPojo>>(answer, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/admin/projects/{projectId}")
    public ResponseEntity<ProjectPojo> getProjectById(@PathVariable long projectId){
        ProjectPojo pojo = projectService.getProjectById(projectId);
        if (pojo != null)
            return new ResponseEntity<ProjectPojo>(pojo, HttpStatus.OK);
        return new ResponseEntity<ProjectPojo>(pojo, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/admin/projects")
    public ResponseEntity<ProjectPojo> postProject(@RequestBody ProjectPojo pojo){
        long id = projectService.postProject(pojo);
        pojo.setId(id);
        return new ResponseEntity<ProjectPojo>(pojo, HttpStatus.OK);
    }

    @PutMapping("/admin/projects/{projectId}")
    public ResponseEntity<ProjectPojo> putProject(@PathVariable long projectId, @RequestBody ProjectPojo pojo){
        ProjectPojo answer = projectService.putProject(projectId,pojo);
        if (answer != null){
            return new ResponseEntity<ProjectPojo>(answer, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<ProjectPojo>(answer, HttpStatus.NOT_FOUND); 
        }
    }

    @DeleteMapping("/admin/projects/{projectId}")
    public ResponseEntity<ProjectPojo> deleteProject(@PathVariable long projectId){
        projectService.deleteProject(projectId);
        return new ResponseEntity<ProjectPojo>(new ProjectPojo(), HttpStatus.OK);
    }

    @GetMapping("/admin/projects/info")
    public ResponseEntity<HashMap<Integer, Integer>> getOpenTasksNumber(){
        HashMap<Integer, Integer> answer = projectService.getInfo();
        return new ResponseEntity<HashMap<Integer, Integer>>(answer, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<List<TaskPojo>> getAllTasks(@PathVariable long projectId){
        List<TaskPojo> pojo = projectService.getAllTasks(projectId);
        if (pojo != null)
            return new ResponseEntity<List<TaskPojo>>(pojo, HttpStatus.OK);
        return new ResponseEntity<List<TaskPojo>>(pojo, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskPojo> getTaskById(@PathVariable long projectId, @PathVariable long taskId){
        TaskPojo pojo = projectService.getTaskById(projectId, taskId);
        if (pojo != null)
            return new ResponseEntity<TaskPojo>(pojo, HttpStatus.OK);
        return new ResponseEntity<TaskPojo>(pojo, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<TaskPojo> postTask(@PathVariable long projectId, @RequestBody TaskPojo pojo){
        TaskPojo answer = projectService.postTask(projectId, pojo);
        return new ResponseEntity<TaskPojo>(answer, HttpStatus.OK);
    }
    
    @PutMapping("/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskPojo> putTask(@PathVariable long projectId, @PathVariable long taskId, @RequestBody TaskPojo pojo){
        TaskPojo answer = projectService.putTask(projectId,taskId,pojo);
        if (answer != null){
            return new ResponseEntity<TaskPojo>(answer, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<TaskPojo>(answer, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskPojo> deleteTask(@PathVariable long projectId, @PathVariable long taskId){
        projectService.deleteTask(projectId,taskId);
        return new ResponseEntity<TaskPojo>(new TaskPojo(), HttpStatus.OK);
    }

    @DeleteMapping("/projects/{projectId}/tasks/remove")
    public ResponseEntity<TaskPojo> deleteDoneTasks(@PathVariable long projectId){
        projectService.deleteDoneTasks(projectId);
        return new ResponseEntity<TaskPojo>(new TaskPojo(), HttpStatus.OK);
    }
}
