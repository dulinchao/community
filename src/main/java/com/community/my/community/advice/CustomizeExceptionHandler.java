package com.community.my.community.advice;

import com.alibaba.fastjson.JSON;
import com.community.my.community.dto.ResultDTO;
import com.community.my.community.exception.CustomizeErrorCode;
import com.community.my.community.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
@Slf4j
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable ex, Model model, HttpServletRequest request, HttpServletResponse response){
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            ResultDTO resultDTO ;
            //返回json
            if(ex instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException)ex);
            }else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            }
            return null;
        }else {
            //返回页面跳转
            if(ex instanceof CustomizeException){
                model.addAttribute("message",ex.getMessage());
            }else {
                log.error("handle error", ex);
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
