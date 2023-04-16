package com.casperinv.service.controller;

import com.casperinv.service.Utils.URLHandler;
import com.casperinv.service.entity.Goals;
import com.casperinv.service.entity.Initiatives;
import com.casperinv.service.service.GoalsService;
import com.casperinv.service.service.InitiativeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/initiatives/")
public class InitiativeController {

    private final String directory="page/initiative";
        private final String baseRedirectUrl="/initiatives/";
    private GoalsService goalsService;
    private InitiativeService initiativeService;

    public InitiativeController(GoalsService goalsService, InitiativeService initiativeService) {
        this.goalsService = goalsService;
        this.initiativeService = initiativeService;
    }

    @GetMapping(value = "/")
    public String getInitiativeListPage(Model model,@RequestParam("goal_sid") String  gsid) {
        Goals goal = goalsService.findBySerialId(gsid);
        List<Map<String, Objects>> initiatives = initiativeService.findByGoal(goal);
        model.addAttribute("initiatives",initiatives);
        model.addAttribute("goal",goal);
        return URLHandler.getRedirectPage(directory, "initiatives");
    }

    @GetMapping(value = "/add")
    public String getInitiativesAddFormPage(Model model,@RequestParam("goal_sid") String gsid) {
        model.addAttribute("goal",goalsService.findBySerialId(gsid));
        return URLHandler.getRedirectPage(directory, "add_initiative");
    }

    @PostMapping(value = "/add")
    public String addInitiativeAction(RedirectAttributes attributes,
                                      @ModelAttribute Initiatives initiatives) {
        attributes.addAttribute("goal_sid",initiatives.getGoal().getSerialid());
        initiativeService.addInitiative(attributes,initiatives);
        return "redirect:"+baseRedirectUrl;
    }

    @GetMapping(value = "/list/task")
    public RedirectView listTask(RedirectAttributes attributes, @RequestParam("id") String serialid) {
        attributes.addAttribute("initiative_sid",serialid);
        return new RedirectView("/tasks/");
    }

    @GetMapping(value = "/add/task")
    public RedirectView addTask(RedirectAttributes attributes, @RequestParam("id") String serialid) {
        attributes.addAttribute("initiative_sid",serialid);
        return new RedirectView("/tasks/add");
    }

    @GetMapping(value = "/edit")
    public String editInitiative(Model model,@RequestParam("id") String serialid) {
        model.addAttribute("goals",goalsService.findAllGoals());
        model.addAttribute("initiative",initiativeService.findBySerialId(serialid));
        return URLHandler.getRedirectPage(directory, "edit_initiative");
    }

    @PostMapping(value = "/update")
    public RedirectView updateInitiative(RedirectAttributes attributes,
                                @ModelAttribute Initiatives initiatives) {
        initiativeService.updateInitiative(attributes,initiatives);
        attributes.addAttribute("goal_sid",initiatives.getGoal().getSerialid());
        return new RedirectView(baseRedirectUrl);
    }

    @GetMapping(value = "/delete")
    public String deleteInitiative(RedirectAttributes attributes, @RequestParam("id") String serialid) {
        attributes.addAttribute("goal_sid",initiativeService.findBySerialId(serialid).getGoal().getSerialid());
        initiativeService.deleteInitiative(attributes,serialid);
        return "redirect:"+baseRedirectUrl;
    }
}
