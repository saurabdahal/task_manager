package com.casperinv.service.controller;

import com.casperinv.service.Utils.URLHandler;
import com.casperinv.service.entity.Job;
import com.casperinv.service.entity.Job;
import com.casperinv.service.service.JobService;
import com.casperinv.service.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/job/")
public class JobController {

    private final String directory="page/job";
        private final String baseRedirectUrl="/job/";
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping(value = "/")
    public String getJobListPage(Model model) {
        List<Job> jobs = jobService.findAllJobs();
        model.addAttribute("jobs",jobs);
        return URLHandler.getRedirectPage(directory, "job");
    }
    @GetMapping(value = "/single")
    public String getJobAddFormPage(Model model,@RequestParam("id") String serial_id) {
        model.addAttribute("job",jobService.findJobBySerialId(serial_id));
        return URLHandler.getRedirectPage(directory, "single_job");
    }
    @GetMapping(value = "/add")
    public String getJobAddFormPage(Model model) {
        return URLHandler.getRedirectPage(directory, "add_job");
    }

    @PostMapping(value = "/add")
    public String addJobAction(RedirectAttributes attributes, @ModelAttribute Job job) {
        jobService.addJob(attributes,job);
        return "redirect:"+baseRedirectUrl;
    }

    @GetMapping(value = "/edit")
    public String editJob(Model model,@RequestParam("id") String serialid) {
        model.addAttribute("job",jobService.findJobBySerialId(serialid));
        return URLHandler.getRedirectPage(directory, "edit_job");
    }

    @PostMapping(value = "/update")
    public String updateJob(RedirectAttributes attributes,
                                @ModelAttribute Job job) {
        jobService.updateJob(attributes,job);
        return "redirect:"+baseRedirectUrl;
    }

    @GetMapping(value = "/delete")
    public String deleteJob(RedirectAttributes attributes, @RequestParam("id") String serialid) {
        jobService.deleteJob(attributes,serialid);
        return "redirect:"+baseRedirectUrl;
    }
}
