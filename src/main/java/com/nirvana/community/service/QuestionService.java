package com.nirvana.community.service;

import com.nirvana.community.dto.ShowQuestion;
import com.nirvana.community.mapper.QuestionMapper;
import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.model.Question;
import com.nirvana.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--12--06--16:02
 **/
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public ShowQuestion queryQuestionById(int id) {

        Question question = questionMapper.selectByPrimaryKey(id);

        User user = userMapper.selectByPrimaryKey(question.getCreator());

        ShowQuestion showQuestion = new ShowQuestion();
        BeanUtils.copyProperties(user,showQuestion);
        BeanUtils.copyProperties(question,showQuestion);
        //由于showquestion中question的id变量名与Question中的不一样，所以需要手动set
        showQuestion.setQuestionId(question.getId());

        return showQuestion;
    }
}
