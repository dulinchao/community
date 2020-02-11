package com.community.my.community.dto;

import lombok.Data;

@Data
public class CommentCreatDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
