package com.casperinv.service.controller;

import com.casperinv.service.Utils.URLHandler;
import com.casperinv.service.service.GoalsService;
import com.casperinv.service.service.InitiativeService;
import com.casperinv.service.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DashboardController {

    private final String directory="page/dashboard";

    private final GoalsService goalsService;
    private final InitiativeService initiativeService;
    private final JobService jobService;

    public DashboardController(GoalsService goalsService, InitiativeService initiativeService, JobService jobService) {
        this.goalsService = goalsService;
        this.initiativeService = initiativeService;
        this.jobService = jobService;
    }

    @GetMapping(value = "/")
    public String getIndexPage(Model model) {
        model.addAttribute("total_goals",goalsService.findAllGoals().size());
        model.addAttribute("completed_goals",goalsService.findCompletedGoals());
        model.addAttribute("inprogress_goals",goalsService.findInProgressGoals());
        model.addAttribute("notstarted_goals",goalsService.findNotStartedGoals());

        model.addAttribute("total_initiative",initiativeService.findAllInitiatives().size());
        model.addAttribute("completed_initiative",initiativeService.findCompletedInitiatives());
        model.addAttribute("inprogress_initiative",initiativeService.findInProgressInitiatives());
        model.addAttribute("notstarted_initiative",initiativeService.findNotStartedInitiatives());

        model.addAttribute("goals",goalsService.findAllCriticalGoalsWithInitiativeCount(15));
        model.addAttribute("initiatives",initiativeService.findAllCriticalInitiatives(15));

        model.addAttribute("total_applied",jobService.findAllJobs().size());
        model.addAttribute("responded",jobService.findResponded());
        model.addAttribute("interviewed",jobService.findInterviewed());

        return URLHandler.getRedirectPage(directory, "dashboard");
    }



}
