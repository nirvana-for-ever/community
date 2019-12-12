package com.nirvana.community.service;

import com.nirvana.community.mapper.QuestionMapper;
import com.nirvana.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--12--02--15:03
 **/
@Service
public class PublishService {

    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 插入问题
     * @param question
     * @return
     */
    public int addOrModifiedQuestion(Question question) {

        if (question.getId()!=0){
            return questionMapper.updateByPrimaryKeySelective(question);
        }else {
            return questionMapper.insertSelective(question);
        }
    }
}
