package com.casperinv.service.repository;

import com.casperinv.service.entity.Initiatives;
import com.casperinv.service.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Tasks,Integer> {
    Tasks findTasksBySerialid(String serialid);
    Integer countAllByCompletedIsTrueAndInitiative(Initiatives initiatives);

    @Query(value = """
select sum(weight) wt from task where completed = true group by ?1
""",nativeQuery = true)
    double avgCompletedWeightForByInitiativeId(int i_id);

    @Query(value = """
select avg(weight) wt from task group by ?1
""",nativeQuery = true)
    double avgTotalWeightForByInitiativeId(int i_id);

    List<Tasks> findAllByInitiative(Initiatives initiatives);
}
