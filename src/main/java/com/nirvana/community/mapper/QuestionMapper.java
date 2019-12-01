package com.nirvana.community.mapper;

import com.nirvana.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--11--30--18:43
 **/
@Mapper
public interface QuestionMapper {

    /**
     * 插入问题
     * @param question
     * @return
     */
    @Insert("insert into question (title,description,tag,creator,gmt_create,gmt_modified) values(#{title},#{description},#{tag},#{creator},#{gmtCreate},#{gmtModified})")
    int insertQuestion(Question question);

    /**
     * 查询所有的问题
     * @return
     */
    @Select("select * from question")
    List<Question> selectAllQuestion();
}
