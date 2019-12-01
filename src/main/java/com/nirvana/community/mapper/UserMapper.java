package com.nirvana.community.mapper;

import com.nirvana.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--11--29--12:56
 **/
@Mapper
public interface UserMapper {

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,picture_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{pictureUrl})")
    int insertUser(User user);

    /**
     * 根据token查询用户
     * @param token
     * @return
     */
    @Select("select * from user where token=#{token}")
    User selectUserByToken(String token);

    /**
     * 根据id查用户
     * @return
     * @param creator
     */
    @Select("select * from user where id=#{creator}")
    User selectUserById(Integer creator);
}
