package com.casperinv.service.controller;

import com.casperinv.service.Utils.URLHandler;
import com.casperinv.service.entity.Goals;
import com.casperinv.service.entity.News;
import com.casperinv.service.service.GoalsService;
import com.casperinv.service.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/news/")
public class NewsController {

    private final String directory="page/news";
        private final String baseRedirectUrl="/news/";
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping(value = "/")
    public String getNewsListPage(Model model) {
        List<News> news = newsService.findAllNews();
        model.addAttribute("news",news);
        return URLHandler.getRedirectPage(directory, "news");
    }

    @GetMapping(value = "/add")
    public String getNewsAddFormPage(Model model) {
        return URLHandler.getRedirectPage(directory, "add_news");
    }

    @PostMapping(value = "/add")
    public String addNewsAction(HttpServletRequest request,@ModelAttribute News news) {
        newsService.addNews(request,news);
        return "redirect:"+baseRedirectUrl;
    }

    @GetMapping(value = "/edit")
    public String editNews(Model model,@RequestParam("id") String serialid) {
        model.addAttribute("news",newsService.findNewsBySerialId(serialid));
        return URLHandler.getRedirectPage(directory, "edit_news");
    }

    @PostMapping(value = "/update")
    public String updateNews(HttpServletRequest servletRequest,
                                @ModelAttribute News news) {
        newsService.updateNews(servletRequest,news);
        return "redirect:"+baseRedirectUrl;
    }

    @GetMapping(value = "/delete")
    public String deleteNews(HttpServletRequest servletRequest, @RequestParam("id") String serialid) {
        newsService.deleteNews(servletRequest,serialid);
        return "redirect:"+baseRedirectUrl;
    }
}
