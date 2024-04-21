package com.casperinv.service.service;

import com.casperinv.service.entity.Goals;
import com.casperinv.service.entity.Initiatives;
import com.casperinv.service.entity.Tasks;
import com.casperinv.service.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public void addTask(RedirectAttributes request, Tasks task){
        try{
            task.setSerialid(UUID.randomUUID().toString());
            task.setDueDate(LocalDate.parse(task.getDueDate().toString()));
            task.setCreatedAt(LocalDate.now());
            task.setUpdatedAt(LocalDate.now());
            taskRepository.save(task);
            request.addFlashAttribute("success","task added successfully");
        }catch (Exception e){
            e.printStackTrace();
            request.addFlashAttribute("error",e.getLocalizedMessage());
        }
    }

    public void updateTask(RedirectAttributes request, Tasks task){
        try{
            Tasks t = taskRepository.findTasksBySerialid(task.getSerialid());
            Initiatives i = initiativeService.findById(task.getInitiative().getId());
            t.setInitiative(i);
            t.setName(task.getName());
            t.setDueDate(LocalDate.parse(task.getDueDate().toString()));
            t.setUpdatedAt(LocalDate.now());
            taskRepository.save(t);
            request.addFlashAttribute("success","task updated successfully");
        }catch (Exception e){
            e.printStackTrace();
            request.addFlashAttribute("error",e.getLocalizedMessage());
        }
    }

    public Tasks findBySerialId(String serialid){
        return taskRepository.findTasksBySerialid(serialid);
    }

    public void deleteTask(RedirectAttributes request, String id){
        try{
            Tasks tasks = taskRepository.findTasksBySerialid(id);
            taskRepository.delete(tasks);
            request.addFlashAttribute("success","task got deleted");
        }catch(Exception e){
            request.addFlashAttribute("error",e.getLocalizedMessage());
        }
    }

    @Transactional
    public void completeTask(RedirectAttributes request,String serialId,int done){
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

    public int findMaxSequenceNumber(Integer initiative_id){
        System.out.println(initiative_id);
        return taskRepository.findMaxSequenceByInitiative(initiative_id);
    }
}
