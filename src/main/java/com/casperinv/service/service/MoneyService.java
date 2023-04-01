package com.casperinv.service.service;

import com.casperinv.service.entity.*;
import com.casperinv.service.repository.MoneyRepository;
import com.casperinv.service.repository.TaskRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class MoneyService {

    private final MoneyRepository moneyRepository;

    public MoneyService(MoneyRepository moneyRepository) {
        this.moneyRepository = moneyRepository;
    }

    public List<Money> findAllMoney(){
        return moneyRepository.findAll();
    }
    public Money findMoneyBySerialId(String serial_id){
        return moneyRepository.findMoneyBySerialid(serial_id);
    }

    public void addMoney(HttpServletRequest request, Money money){
        try{
            money.setSerialid(UUID.randomUUID().toString());
            money.setTransactionDate(LocalDate.parse(money.getTransactionDate().toString()));
            money.setCreatedAt(LocalDate.now());
            money.setUpdatedAt(LocalDate.now());
            moneyRepository.save(money);
            request.getSession().setAttribute("success","money record added successfully");
        }catch (Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("error",e.getLocalizedMessage());
        }
    }

    public void updateMoney(HttpServletRequest request, Money money){
        try{
            Money m = moneyRepository.findMoneyBySerialid(money.getSerialid());
            m.setType(money.getType());
            m.setAmount(money.getAmount());
            m.setRemark(money.getRemark());
            m.setTransactionDate(LocalDate.parse(money.getTransactionDate().toString()));
            m.setUpdatedAt(LocalDate.now());
            moneyRepository.save(m);
            request.getSession().setAttribute("success","money record updated successfully");
        }catch (Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("error",e.getLocalizedMessage());
        }
    }


    public void deleteMoney(HttpServletRequest request, String id){
        try{
            Money money = moneyRepository.findMoneyBySerialid(id);
            moneyRepository.delete(money);
            request.getSession().setAttribute("success","money record got deleted");
        }catch(Exception e){
            request.getSession().setAttribute("error",e.getLocalizedMessage());
        }
    }
    public double findTotalBalance(){
        return moneyRepository.findBalance();
    }

}
