package com.casperinv.service.controller;

import com.casperinv.service.Utils.URLHandler;
import com.casperinv.service.entity.Money;
import com.casperinv.service.entity.News;
import com.casperinv.service.service.MoneyService;
import com.casperinv.service.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/money/")
public class MoneyController {

    private final String directory="page/money";
        private final String baseRedirectUrl="/money/";
    private final MoneyService moneyService;

    public MoneyController(MoneyService moneyService) {
        this.moneyService = moneyService;
    }

    @GetMapping(value = "/")
    public String getMoneyListPage(Model model) {
        List<Money> money = moneyService.findAllMoney();
        model.addAttribute("moneys",money);
        return URLHandler.getRedirectPage(directory, "money");
    }

    @GetMapping(value = "/add")
    public String getMoneyAddFormPage(Model model) {
        return URLHandler.getRedirectPage(directory, "add_money");
    }

    @PostMapping(value = "/add")
    public String addMoneyAction(HttpServletRequest request,@ModelAttribute Money money) {
        moneyService.addMoney(request,money);
        return "redirect:"+baseRedirectUrl;
    }

    @GetMapping(value = "/edit")
    public String editMoney(Model model,@RequestParam("id") String serialid) {
        model.addAttribute("money",moneyService.findMoneyBySerialId(serialid));
        return URLHandler.getRedirectPage(directory, "edit_money");
    }

    @PostMapping(value = "/update")
    public String updateMoney(HttpServletRequest servletRequest,
                                @ModelAttribute Money money) {
        moneyService.updateMoney(servletRequest,money);
        return "redirect:"+baseRedirectUrl;
    }

    @GetMapping(value = "/delete")
    public String deleteMoney(HttpServletRequest servletRequest, @RequestParam("id") String serialid) {
        moneyService.deleteMoney(servletRequest,serialid);
        return "redirect:"+baseRedirectUrl;
    }


}
