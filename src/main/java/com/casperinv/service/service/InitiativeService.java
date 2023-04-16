package com.casperinv.service.service;

import com.casperinv.service.entity.Goals;
import com.casperinv.service.entity.Initiatives;
import com.casperinv.service.repository.InitiativeRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class InitiativeService {

    private final InitiativeRepository initiativeRepository;
    private final GoalsService goalsService;

    public InitiativeService(InitiativeRepository initiativeRepository, GoalsService goalsService) {
        this.initiativeRepository = initiativeRepository;
        this.goalsService = goalsService;
    }

    public int findCompletedInitiatives(){
        return initiativeRepository.countInitiativesByCompletionGreaterThanEqual(100.0);
    }

    public int findInProgressInitiatives(){
        return initiativeRepository.countInitiativesByCompletionLessThanAndCompletionGreaterThan(100.0,0.0);
    }

    public int findNotStartedInitiatives(){
        return initiativeRepository.countInitiativesByCompletionEquals(0.0);
    }
    public List<Initiatives> findAllInitiatives(){
        return initiativeRepository.findAll();
    }

    public List<Map<String, Object>> findAllCriticalInitiatives(int days_rem){
        return initiativeRepository.findAllCriticalInitiatives(days_rem);
    }
    public void addInitiative(RedirectAttributes attributes, Initiatives initiative){
        try{
            initiative.setSerialid(UUID.randomUUID().toString());
            initiative.setDueDate(LocalDate.parse(initiative.getDueDate().toString()));
            initiative.setCreatedAt(LocalDate.now());
            initiative.setUpdatedAt(LocalDate.now());
            initiativeRepository.save(initiative);
            attributes.addFlashAttribute("success","initiative added successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error",e.getLocalizedMessage());
        }
    }

    public void updateInitiative(RedirectAttributes attributes, Initiatives initiatives){
        try{
            Initiatives initiative = initiativeRepository.findInitiativeBySerialid(initiatives.getSerialid());
            Goals g = goalsService.findBylId(initiatives.getGoal().getId());
            initiative.setGoal(g);
            initiative.setName(initiatives.getName());
            initiative.setDueDate(LocalDate.parse(initiatives.getDueDate().toString()));
            initiative.setUpdatedAt(LocalDate.now());
            initiativeRepository.save(initiative);
            attributes.addFlashAttribute("success","initiative updated successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error",e.getLocalizedMessage());
        }
    }

    public Initiatives findBySerialId(String serialid){
        return initiativeRepository.findInitiativeBySerialid(serialid);
    }

    public List<Map<String, Objects>> findByGoal(Goals goal){
        return initiativeRepository.findAllByGoal(goal.getId());
    }

    public double findTotalCompletionByGoal(int goalId){
        return initiativeRepository.findTotalCompletion(goalId);
    }

    public Initiatives findById(int id){
        return initiativeRepository.findById(id).get();
    }

    public void deleteInitiative(RedirectAttributes attributes, String id){
        try{
            Initiatives initiative = initiativeRepository.findInitiativeBySerialid(id);
            initiativeRepository.delete(initiative);
            attributes.addFlashAttribute("success","initiative got deleted");
        }catch(Exception e){
            attributes.addFlashAttribute("error",e.getLocalizedMessage());
        }
    }

}
