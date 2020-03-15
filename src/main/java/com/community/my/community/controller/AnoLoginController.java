package com.community.my.community.controller;

import com.community.my.community.mapper.UserMapper;
import com.community.my.community.model.User;
import com.community.my.community.model.UserExample;
import com.community.my.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 匿名登陆的控制器
 */
@Controller
public class AnoLoginController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @GetMapping("/anologin")
    public String anoLogin(HttpServletResponse response){
        User user = new User(); //新建一个User类存储登录用户信息，并写入数据库
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        long usersNum = userMapper.countByExample(new UserExample());   //统计当前的用户数量
        user.setName("匿名用户"+usersNum);
        user.setAccountId(String.valueOf(usersNum));
        user.setAvatarUrl("/images/default-avatar.png");
        userService.createOrUpdate(user);
        response.addCookie(new Cookie("token",token)); //user不为空。登陆成功，写cookie
        return "redirect:/";    //重定向到主页
    }

}
