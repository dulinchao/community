package com.community.my.community.service;

import com.community.my.community.dto.PaginationDTO;
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
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();  //此类包含此页面所有要展示的问题以及页码信息
        Integer totalPage;
        Integer totalCount = questionMapper.count();
        if(totalCount % size == 0){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount/size + 1;
        }
        if(page<1){
            page =1 ;
        }
        if(page > totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset,size);//从数据库中获取Question实例列表
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for(Question question:questions){
            User user = userMapper.findById(question.getCreator());//用creator(即id)到user表中查询获取用户对象
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOS);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();  //此类包含此页面所有要展示的问题以及页码信息
        Integer totalPage;
        Integer totalCount = questionMapper.countByUserId(userId);
        if(totalCount % size == 0){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount/size + 1;
        }
        if(page<1){
            page =1 ;
        }
        if(page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.listbyUser(userId,offset,size);//从数据库中获取Question实例列表
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for(Question question:questions){
            User user = userMapper.findById(question.getCreator());//用creator(即id)到user表中查询获取用户对象
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOS);
        return paginationDTO;
    }
}
