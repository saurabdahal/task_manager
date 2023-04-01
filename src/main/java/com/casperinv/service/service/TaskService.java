package com.casperinv.service.service;

import com.casperinv.service.entity.Goals;
import com.casperinv.service.entity.Initiatives;
import com.casperinv.service.entity.Tasks;
import com.casperinv.service.repository.TaskRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final InitiativeService initiativeService;
    private final GoalsService goalsService;
    private final TaskRepository taskRepository;
    public TaskService(InitiativeService initiativeService, GoalsService goalsService, TaskRepository taskRepository) {
        this.initiativeService = initiativeService;
        this.goalsService = goalsService;
        this.taskRepository = taskRepository;
    }

    public List<Tasks> findAllTasks(){
        return taskRepository.findAll();
    }

    public List<Tasks> findAllTasksByInitiatives(Initiatives initiatives){
        return taskRepository.findAllByInitiative(initiatives);
    }
    public void addTask(HttpServletRequest request, Tasks task){
        try{
            task.setSerialid(UUID.randomUUID().toString());
            task.setDueDate(LocalDate.parse(task.getDueDate().toString()));
            task.setCreatedAt(LocalDate.now());
            task.setUpdatedAt(LocalDate.now());
            taskRepository.save(task);
            request.getSession().setAttribute("success","task added successfully");
        }catch (Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("error",e.getLocalizedMessage());
        }
    }

    public void updateTask(HttpServletRequest request, Tasks task){
        try{
            Tasks t = taskRepository.findTasksBySerialid(task.getSerialid());
            Initiatives i = initiativeService.findById(task.getInitiative().getId());
            t.setInitiative(i);
            t.setName(task.getName());
            t.setDueDate(LocalDate.parse(task.getDueDate().toString()));
            t.setUpdatedAt(LocalDate.now());
            taskRepository.save(t);
            request.getSession().setAttribute("success","task updated successfully");
        }catch (Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("error",e.getLocalizedMessage());
        }
    }

    public Tasks findBySerialId(String serialid){
        return taskRepository.findTasksBySerialid(serialid);
    }

    public void deleteTask(HttpServletRequest request, String id){
        try{
            Tasks tasks = taskRepository.findTasksBySerialid(id);
            taskRepository.delete(tasks);
            request.getSession().setAttribute("success","task got deleted");
        }catch(Exception e){
            request.getSession().setAttribute("error",e.getLocalizedMessage());
        }
    }

    @Transactional
    public void completeTask(HttpServletRequest request,String serialId,int done){
        boolean status = done == 1;
        Tasks task = taskRepository.findTasksBySerialid(serialId);
        Initiatives initiative = task.getInitiative();
        task.setCompleted(status);
        taskRepository.save(task);
        Goals goal = initiative.getGoal();
        int all_tasks = taskRepository.findAllByInitiative(initiative).size();
        double completed_task = taskRepository.countAllByCompletedIsTrueAndInitiative(initiative);
        double percentage = (completed_task/all_tasks)*100.0;
        initiative.setCompletion(percentage);
        initiativeService.updateInitiative(request,initiative);
        int initiativeWeight = initiativeService.findByGoal(goal).size()*100;
        double initiativeCompletion = initiativeService.findTotalCompletionByGoal(goal.getId());
        double ini_percentage = (initiativeCompletion/initiativeWeight)*100;
        goal.setCompletion(ini_percentage);
        goalsService.updateGoal(request,goal);
    }
}
