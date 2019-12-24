package com.nirvana.community.service;

import com.nirvana.community.common.Constants;
import com.nirvana.community.dto.ShowQuestion;
import com.nirvana.community.enums.CustomizeErrorCode;
import com.nirvana.community.exception.CustomizeException;
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

    /**
     *
     * @param id 问题id
     * @param isRead true表示浏览问题，需要在问题的浏览数上加1，false表示仅仅是业务里面查询这个问题而已
     */
    public ShowQuestion queryQuestionById(int id,boolean isRead) {


        Question question = questionMapper.selectByPrimaryKey(id);
        if (null == question) {
            //自定义的异常
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        //@TODO 在高并发的时候可能会出现多个人拿到相同的浏览数，然后更新的时候多个人重复更新相同的浏览数，可通过悲观锁或者在增加阅读数的sql语句中使用set readCount=readCount+1
        //判断isRead的值，true则浏览数加1
        if (isRead){
            question.setReadCount(question.getReadCount()+1);
            questionMapper.updateByPrimaryKeySelective(question);
        }

        User user = userMapper.selectByPrimaryKey(question.getCreator());
        ShowQuestion showQuestion = new ShowQuestion();
        BeanUtils.copyProperties(user,showQuestion);
        BeanUtils.copyProperties(question,showQuestion);
        //由于showquestion中question的id变量名与Question中的不一样，所以需要手动set
        showQuestion.setQuestionId(question.getId());

        return showQuestion;
    }
}
