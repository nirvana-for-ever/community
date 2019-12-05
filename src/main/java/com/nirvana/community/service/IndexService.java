package com.nirvana.community.service;

import com.nirvana.community.mapper.QuestionMapper;
import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.model.Question;
import com.nirvana.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--12--02--15:03
 **/
@Service
public class IndexService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public List<Question> queryQuestionByPage(Map<String, Object> pageMap) {
        return questionMapper.selectQuestionByPage(pageMap);
    }


    public Integer queryQuestionCount() {
        return questionMapper.selectQuestionCount();
    }


    public User queryUserByCreator(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public Integer queryQuestionCount(Integer creator) {
        return questionMapper.selectQuestionCountByCreator(creator);
    }
}
