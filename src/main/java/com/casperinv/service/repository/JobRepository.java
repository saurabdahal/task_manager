package com.casperinv.service.repository;

import com.casperinv.service.entity.Job;
import com.casperinv.service.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job,Integer> {
    Job findJobBySerialid(String serial_id);

    int countJobByResponseIsTrue();
    int countJobByInterviewIsTrue();
}
