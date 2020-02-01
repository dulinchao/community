package com.community.my.community.service;

import com.community.my.community.dto.QuestionDTO;
import com.community.my.community.mapper.QuestionMapper;
import com.community.my.community.mapper.UserMapper;
import com.community.my.community.model.Question;
import com.community.my.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    public List<QuestionDTO> list() {
        List<Question> questions = questionMapper.list();//从数据库中获取Question实例列表
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for(Question question:questions){
            User user = userMapper.findById(question.getCreator());//用creator(即id)到user表中查询获取用户对象
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }
}
