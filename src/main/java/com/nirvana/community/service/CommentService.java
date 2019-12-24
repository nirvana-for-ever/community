package com.nirvana.community.service;

import com.nirvana.community.common.Constants;
import com.nirvana.community.enums.CustomizeErrorCode;
import com.nirvana.community.exception.CustomizeException;
import com.nirvana.community.mapper.CommentMapper;
import com.nirvana.community.mapper.QuestionMapper;
import com.nirvana.community.model.Comment;
import com.nirvana.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--12--24--13:08
 **/
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public void addComment(Comment comment) {

        if (comment.getType()==null){
            throw new CustomizeException(CustomizeErrorCode.OTHER_ERROR);
        }
        if (comment.getType().equals(Constants.TO_QUESTION)){
            //对于问题的评论
            //判断问题是否存在，不存在需要报错
            Question question = questionMapper.selectByPrimaryKey(comment.getqId());
            if (null == question) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            //将插入评论所属的问题的评论数加1
            question.setCommentCount(question.getCommentCount()+1);
            questionMapper.updateByPrimaryKeySelective(question);

        }else if (comment.getType().equals(Constants.TO_COMMENT)){
            //对于评论的评论
            Comment parentComment = commentMapper.selectByPrimaryKey(comment.getqId());
            if (null == parentComment) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
        }
        
    }
}
