package com.casperinv.service.service;

import com.casperinv.service.entity.Money;
import com.casperinv.service.entity.News;
import com.casperinv.service.repository.MoneyRepository;
import com.casperinv.service.repository.NewsRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService( NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> findAllNews(){
        return newsRepository.findAll();
    }

    public News findNewsBySerialId(String serial_id){
        return newsRepository.findNewsBySerialid(serial_id);
    }
    public void addNews(HttpServletRequest request, News news){
        try{
            news.setSerialid(UUID.randomUUID().toString());
            news.setOccouredDate(LocalDate.parse(news.getOccouredDate().toString()));
            news.setCreatedAt(LocalDate.now());
            news.setUpdatedAt(LocalDate.now());
            newsRepository.save(news);
            request.getSession().setAttribute("success","news record added successfully");
        }catch (Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("error",e.getLocalizedMessage());
        }
    }

    public void updateNews(HttpServletRequest request, News news){
        try{
            News n = newsRepository.findNewsBySerialid(news.getSerialid());
            n.setRemark(news.getRemark());
            n.setType(news.getType());
            n.setDescription(news.getDescription());
            n.setOccouredDate(LocalDate.parse(news.getOccouredDate().toString()));
            n.setUpdatedAt(LocalDate.now());
            newsRepository.save(n);
            request.getSession().setAttribute("success","news record updated successfully");
        }catch (Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("error",e.getLocalizedMessage());
        }
    }


    public void deleteNews(HttpServletRequest request, String id){
        try{
            News news = newsRepository.findNewsBySerialid(id);
            newsRepository.delete(news);
            request.getSession().setAttribute("success","news record got deleted");
        }catch(Exception e){
            request.getSession().setAttribute("error",e.getLocalizedMessage());
        }
    }

}
