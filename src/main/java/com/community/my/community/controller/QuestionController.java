package com.community.my.community.controller;

import com.community.my.community.dto.CommentDTO;
import com.community.my.community.dto.QuestionDTO;
import com.community.my.community.enums.CommentTypeEnum;
import com.community.my.community.service.CommentService;
import com.community.my.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Long id,
                           Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        questionService.incView(id);    //累加阅读数
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }
}
