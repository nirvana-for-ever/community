package com.nirvana.community.service;

import com.nirvana.community.dto.ShowQuestion;
import com.nirvana.community.enums.CustomizeErrorCode;
import com.nirvana.community.exception.CustomizeException;
import com.nirvana.community.mapper.QuestionMapper;
import com.nirvana.community.mapper.UserMapper;
import com.nirvana.community.model.Question;
import com.nirvana.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * 根据标签查找相关问题
     */
    public List<ShowQuestion> queryRelatedQuestion(String tag,Integer id) {

        //根据逗号将标签拆分
        List<String> tags = Arrays.stream(StringUtils.split(tag, ",")).map(s -> "%" + s + "%").collect(Collectors.toList());

        //转化成map传进去
        Map<String,Object> map = new HashMap<>();
        map.put("tags",tags);
        map.put("id",id);

        List<Question> questions = questionMapper.selectLikeByTags(map);

        return questions.stream().map(question -> {
            ShowQuestion showQuestion = new ShowQuestion();
            BeanUtils.copyProperties(question, showQuestion);
            showQuestion.setQuestionId(question.getId());
            return showQuestion;
        }).collect(Collectors.toList());
    }
}
