package com.community.my.community.controller;

import com.community.my.community.dto.PaginationDTO;
import com.community.my.community.mapper.UserMapper;
import com.community.my.community.model.User;
import com.community.my.community.service.NotificationService;
import com.community.my.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name="page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size){    //动态的路由页面
        User user = (User)request.getSession().getAttribute("user");
        if(user == null)    return "redirect:/";
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO = questionService.list(user.getId(),page,size);
            model.addAttribute("pagination",paginationDTO);//写入帖子的信息
        }else if("replies".equals(action)){
            PaginationDTO paginationDTO = notificationService.list(user.getId(),page,size);
            model.addAttribute("pagination",paginationDTO);//写入帖子的信息
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","我的回复");
        }
        return "profile";
    }
}
