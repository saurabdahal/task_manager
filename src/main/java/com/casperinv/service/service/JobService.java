package com.casperinv.service.service;

import com.casperinv.service.entity.Job;
import com.casperinv.service.repository.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> findAllJobs(){
        return jobRepository.findAll();
    }

    public Job findJobBySerialId(String serial_id){
        return jobRepository.findJobBySerialid(serial_id);
    }

    public int findResponded(){
        return jobRepository.countJobByResponseIsTrue();
    }

    public int findInterviewed(){
        return jobRepository.countJobByInterviewIsTrue();
    }

    public void addJob(RedirectAttributes attributes, Job job){
        try{
            job.setSerialid(UUID.randomUUID().toString());
            job.setAppliedDate(LocalDate.parse(job.getAppliedDate().toString()));
            job.setCreatedAt(LocalDate.now());
            job.setUpdatedAt(LocalDate.now());
            jobRepository.save(job);
            attributes.addFlashAttribute("success","job record added successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error",e.getLocalizedMessage());
        }
    }

    public void updateJob(RedirectAttributes attributes, Job job){
        try{
            job.setUpdatedAt(LocalDate.now());
            jobRepository.save(job);
            attributes.addFlashAttribute("success","job record updated successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error",e.getLocalizedMessage());
        }
    }


    public void deleteJob(RedirectAttributes attributes, String id){
        try{
            Job job = jobRepository.findJobBySerialid(id);
            jobRepository.delete(job);
            attributes.addFlashAttribute("success","job record got deleted");
        }catch(Exception e){
            attributes.addFlashAttribute("error",e.getLocalizedMessage());
        }
    }

}
