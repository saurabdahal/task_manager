package com.casperinv.service.controller;

import com.casperinv.service.Utils.URLHandler;
import com.casperinv.service.entity.Initiatives;
import com.casperinv.service.entity.Tasks;
import com.casperinv.service.service.GoalsService;
import com.casperinv.service.service.InitiativeService;
import com.casperinv.service.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/tasks/")
public class TaskController {

    private final String directory="page/task";
    private final String baseRedirectUrl="/tasks/";
    private final TaskService taskService;
    private final InitiativeService initiativeService;

    public TaskController(TaskService taskService, InitiativeService initiativeService) {
        this.taskService = taskService;
        this.initiativeService = initiativeService;
    }

    @GetMapping(value = "/")
    public String getTaskListPage(Model model,@RequestParam("initiative_sid") String  isid) {
        Initiatives initiatives = initiativeService.findBySerialId(isid);
        List<Tasks> tasks = taskService.findAllTasksByInitiatives(initiatives);
        model.addAttribute("tasks",tasks);
        model.addAttribute("initiative",initiatives);
        return URLHandler.getRedirectPage(directory, "tasks");
    }

    @GetMapping(value = "/add")
    public String getTaskAddFormPage(Model model,@RequestParam("initiative_sid") String sid) {
        model.addAttribute("initiative",initiativeService.findBySerialId(sid));
        return URLHandler.getRedirectPage(directory, "add_task");
    }

    @PostMapping(value = "/add")
    public String addTaskAction(RedirectAttributes attributes, @ModelAttribute Tasks tasks) {
        taskService.addTask(attributes,tasks);
        attributes.addAttribute("initiative_sid",tasks.getInitiative().getSerialid());
        return "redirect:"+baseRedirectUrl;
    }

    @GetMapping(value = "/edit")
    public String editTask(Model model,@RequestParam("id") String serialid) {
        model.addAttribute("task",taskService.findBySerialId(serialid));
        model.addAttribute("initiatives",initiativeService.findAllInitiatives());
        return URLHandler.getRedirectPage(directory, "edit_task");
    }

    @PostMapping(value = "/update")
    public String updateTask(RedirectAttributes attributes,
                             @ModelAttribute Tasks tasks) {
        attributes.addAttribute("initiative_sid",tasks.getInitiative().getSerialid());
        taskService.updateTask(attributes,tasks);
        return "redirect:"+baseRedirectUrl;
    }

    @GetMapping(value = "/delete")
    public String deleteTask(RedirectAttributes attributes, @RequestParam("id") String serialid) {
        attributes.addAttribute("initiative_sid",taskService.findBySerialId(serialid).getInitiative().getSerialid());
        taskService.deleteTask(attributes,serialid);
        return "redirect:"+baseRedirectUrl;
    }

    @GetMapping(value = "/done")
    public String doneTask(RedirectAttributes attributes, @RequestParam("id") String serialid,
                           @RequestParam("done") int done) {
        attributes.addAttribute("initiative_sid",taskService.findBySerialId(serialid).getInitiative().getSerialid());
        taskService.completeTask(attributes,serialid,done);
        return "redirect:"+baseRedirectUrl;
    }
}
