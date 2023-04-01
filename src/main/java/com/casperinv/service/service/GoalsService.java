package com.casperinv.service.service;

import com.casperinv.service.entity.Goals;
import com.casperinv.service.repository.GoalsRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GoalsService {

    private final GoalsRepository goalsRepository;

    public GoalsService(GoalsRepository goalsRepository) {
        this.goalsRepository = goalsRepository;
    }

    public List<Goals> findAllGoals(){
        return goalsRepository.findAll();
    }

    public int findCompletedGoals(){
        return goalsRepository.countGoalsByCompletionGreaterThanEqual(100.0);
    }

    public int findInProgressGoals(){
        return goalsRepository.countGoalsByCompletionLessThanAndCompletionGreaterThan(100.0,0.0);
    }

    public int findNotStartedGoals(){
        return goalsRepository.countGoalsByCompletionEquals(0.0);
    }
    public List<Map<String, Object>> findAllGoalsWithInitiativeCount(){
        return goalsRepository.findAllGoalsWithICount();
    }

    public List<Map<String, Object>> findAllCriticalGoalsWithInitiativeCount(int days_rem){
        return goalsRepository.findAllGoalsWithICount(days_rem);
    }

    public void addgoal(HttpServletRequest request, Goals goals){
        try{
            goals.setSerialid(UUID.randomUUID().toString());
            goals.setName(goals.getName());
            goals.setPurpose(goals.getPurpose());
            goals.setDeadline(LocalDate.parse(goals.getDeadline().toString()));
            goals.setCreatedAt(LocalDate.now());
            goals.setUpdatedAt(LocalDate.now());
            goalsRepository.save(goals);
            request.getSession().setAttribute("success","goal added successfully");
        }catch (Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("error",e.getLocalizedMessage());
        }
    }

    public void updateGoal(HttpServletRequest request, Goals goals){
        try{
            System.out.println(goals.getSerialid());
            Goals g1 = goalsRepository.findGoalsBySerialid(goals.getSerialid());
            g1.setName(goals.getName());
            g1.setPurpose(goals.getPurpose());
            g1.setDeadline(LocalDate.parse(goals.getDeadline().toString()));
            g1.setUpdatedAt(LocalDate.now());
            goalsRepository.save(g1);
            request.getSession().setAttribute("success","goal updated successfully");
        }catch (Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("error",e.getLocalizedMessage());
        }
    }

    public Goals findBySerialId(String serialid){
        return goalsRepository.findGoalsBySerialid(serialid);
    }

    public Goals findBylId(int serialid){
        return goalsRepository.findById(serialid).get();
    }

    public void deleteGoal(HttpServletRequest request, String id){
        try{
            Goals goals = goalsRepository.findGoalsBySerialid(id);
            goalsRepository.delete(goals);
            request.getSession().setAttribute("success","goals got deleted");
        }catch(Exception e){
            request.getSession().setAttribute("error",e.getLocalizedMessage());
        }
    }

}
