package com.nirvana.community.service;

import com.nirvana.community.common.Constants;
import com.nirvana.community.dto.InsertComment;
import com.nirvana.community.dto.ShowComment;
import com.nirvana.community.enums.CustomizeErrorCode;
import com.nirvana.community.exception.CustomizeException;
import com.nirvana.community.mapper.CommentMapper;
import com.nirvana.community.mapper.QuestionMapper;
import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.model.Comment;
import com.nirvana.community.model.Question;
import com.nirvana.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private UserMapper userMapper;

    @Transactional
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

    public List<ShowComment> queryCommentByQId(int id) {

        //根据问题的id查询到该问题下的评论
        List<Comment> commentList = commentMapper.selectByQId(id);
        //判断该问题是否有评论，如果没有则直接返回一个空
        if (0 == commentList.size()) {
            return new ArrayList<>();
        }

        //将评论的评论者放入list中并去重
        List<Integer> commentatorList = commentList.stream().map(Comment::getCommentator).distinct().collect(Collectors.toList());
        //根据评论者查询到用户信息
        Map<Integer, User> userMap = userMapper.selectByPrimaryKeyList(commentatorList);

        //将评论与用户挂钩，放入到ShowComment中，形成一个list
        return commentList.stream().map(comment -> {
            ShowComment showComment = new ShowComment();
            BeanUtils.copyProperties(comment, showComment);
            User user = userMap.get(comment.getCommentator());
            showComment.setUser(user);
            return showComment;
        }).collect(Collectors.toList());
    }
}
