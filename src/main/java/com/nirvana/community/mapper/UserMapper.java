package com.nirvana.community.mapper;

import com.nirvana.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

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
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insertUser(User user);

}
