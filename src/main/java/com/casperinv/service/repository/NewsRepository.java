package com.casperinv.service.repository;

import com.casperinv.service.entity.Money;
import com.casperinv.service.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News,Integer> {
    News findNewsBySerialid(String serial_id);

    List<News> findAllByTypeEquals(int type);
}
