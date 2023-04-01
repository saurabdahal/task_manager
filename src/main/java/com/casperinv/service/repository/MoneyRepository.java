package com.casperinv.service.repository;

import com.casperinv.service.entity.Money;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MoneyRepository extends JpaRepository<Money,Integer> {
    Money findMoneyBySerialid(String serial_id);
    List<Money> findAllByTypeEquals(int type);

    @Query(value = """
select case when balance is null then 0 else balance end from (with income as (
    select sum(amount) inc from money where type=1),
     expense as (
         select sum(amount) exp from money where type=0)
select income.inc-expense.exp balance from income,expense) as blnc;
""",nativeQuery = true)
    double findBalance();
}
