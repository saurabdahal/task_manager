package com.casperinv.service.repository;

import com.casperinv.service.entity.Goals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface GoalsRepository extends JpaRepository<Goals,Integer> {
    Goals findGoalsBySerialid(String serialid);

    @Query(value = """
with initiative_count as (
    select goal_id,count(*) i_count from initiative group by goal_id
)
select gs.*,cast(DATE_PART('day', cast(deadline as timestamp) - cast(CURRENT_DATE as timestamp)) as integer) 
          days_remaining,case when ic.i_count is null then 0 else ic.i_count end from goals gs left join initiative_count ic on gs.id = ic.goal_id order by days_remaining
""",nativeQuery = true)
    List<Map<String, Object>> findAllGoalsWithICount();

    @Query(value = """
select * from (with initiative_count as ( select goal_id,count(*) i_count from initiative group by goal_id
)
select gs.serial_id,gs.name,gs.completion,DATE_PART('day', cast(deadline as timestamp) - cast(CURRENT_DATE as timestamp)) days_remaining
,ic.i_count from goals gs left join initiative_count ic
on gs.id = ic.goal_id where gs.completion < 100.0 and gs.completion > 0.0 order by days_remaining ) as result where days_remaining < ?1
""",nativeQuery = true)
    List<Map<String, Object>> findAllGoalsWithICount(int days_rem);

    Integer countGoalsByCompletionGreaterThanEqual(double completion_rate);

    Integer countGoalsByCompletionLessThanAndCompletionGreaterThan(double upper, double lower);

    Integer countGoalsByCompletionEquals(double completion_rate);
}
