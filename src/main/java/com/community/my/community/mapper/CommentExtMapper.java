package com.community.my.community.mapper;


import com.community.my.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment record);
}