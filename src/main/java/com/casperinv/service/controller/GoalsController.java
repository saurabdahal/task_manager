package com.casperinv.service.controller;

import com.casperinv.service.Utils.URLHandler;
import com.casperinv.service.entity.Goals;
import com.casperinv.service.service.GoalsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/goals/")
public class GoalsController {

    private final String directory="page/goals";
    private final String initiative_directory="page/initiative";
        private final String baseRedirectUrl="/goals/";
    private GoalsService goalsService;

    public GoalsController(GoalsService goalsService) {
        this.goalsService = goalsService;
    }

    @GetMapping(value = "/")
    public String getGoalsListPage(Model model) {
        List<Map<String,Object>> goals = goalsService.findAllGoalsWithInitiativeCount();
        model.addAttribute("goals",goals);
        return URLHandler.getRedirectPage(directory, "goals");
    }

    @GetMapping(value = "/add")
    public String getGoalsAddFormPage(Model model) {
        return URLHandler.getRedirectPage(directory, "add_goal");
    }

    @PostMapping(value = "/add")
    public String addGoalAction(RedirectAttributes attributes,@ModelAttribute Goals goal) {
        goalsService.addgoal(attributes,goal);
        return "redirect:"+baseRedirectUrl;
    }

    @GetMapping(value = "/edit")
    public String editGoal(Model model,@RequestParam("id") String serialid) {
        model.addAttribute("goal",goalsService.findBySerialId(serialid));
        return URLHandler.getRedirectPage(directory, "edit_goal");
    }

    @GetMapping(value = "/add/initiative")
    public RedirectView addInitiative(RedirectAttributes attributes,@RequestParam("id") String serialid) {
        attributes.addAttribute("goal_sid",serialid);
        return new RedirectView("/initiatives/add/");
    }

    @GetMapping(value = "/list/initiatives")
    public RedirectView listInitiative(RedirectAttributes attributes, @RequestParam("id") String serialid) {
        attributes.addAttribute("goal_sid",serialid);
        return new RedirectView("/initiatives/");
    }

    @PostMapping(value = "/update")
    public String updateGoal(RedirectAttributes attributes,
                                @ModelAttribute Goals goals) {
        goalsService.updateGoal(attributes,goals);
        return "redirect:"+baseRedirectUrl;
    }

    @GetMapping(value = "/delete")
    public String deleteGoal(RedirectAttributes attributes, @RequestParam("id") String serialid) {
        goalsService.deleteGoal(attributes,serialid);
        return "redirect:"+baseRedirectUrl;
    }
}
