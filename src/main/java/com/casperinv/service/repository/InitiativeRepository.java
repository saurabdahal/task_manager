package com.casperinv.service.repository;

import com.casperinv.service.entity.Goals;
import com.casperinv.service.entity.Initiatives;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface InitiativeRepository extends JpaRepository<Initiatives,Integer> {
    Initiatives findInitiativeBySerialid(String serialid);
    @Query(value = """
with total_task as (
    select initiative_id, count(*) ttc from task group by initiative_id
), incomp_task as (
    select initiative_id, count(*) itc from task where completed=false group by initiative_id
)
select i.*,case when tt.ttc is null then 0 else tt.ttc end, case when it.itc is null then 0 else it.itc end 
from total_task tt left join incomp_task it on it.initiative_id=tt.initiative_id right join initiative i 
on i.id=tt.initiative_id where i.goal_id=?1
""",nativeQuery = true)
    List<Map<String, Objects>> findAllByGoal(int id);

    @Query(value = """
select * from ( select completion,name,serial_id,DATE_PART('day', cast(due_date as timestamp) - cast(CURRENT_DATE as timestamp))
  days_remaining from initiative where completion > 0.0 and completion < 100.0 order by days_remaining ) as result
  where days_remaining < 15;""",nativeQuery = true)
    List<Map<String, Object>> findAllCriticalInitiatives(int days_rem);

    @Query(value = """
select sum(completion) from initiative where goal_id=?1
""",nativeQuery = true)
    double findTotalCompletion(int id);

    Integer countInitiativesByCompletionGreaterThanEqual(double completion_rate);

    Integer countInitiativesByCompletionLessThanAndCompletionGreaterThan(double upper,double lower);

    Integer countInitiativesByCompletionEquals(double completion_rate);

    @Query(value = """
with total_task as (
    select initiative_id, count(*) ttc from task group by initiative_id
), incomp_task as (
    select initiative_id, count(*) itc from task where completed=false group by initiative_id
)
select i.*,tt.ttc,it.itc
from total_task tt left join incomp_task it on it.initiative_id=tt.initiative_id right join initiative i
on i.id=tt.initiative_id;
""",nativeQuery = true)
    List<Initiatives> findAllInitiatives();
}
