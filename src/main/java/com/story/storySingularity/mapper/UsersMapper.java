package com.story.storySingularity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.story.storySingularity.model.po.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiaban
 */
public interface UsersMapper extends BaseMapper<Users> {
    @Select(value = "select * from users where username=#{username} and password = #{password}")
//使用注解方式，也可用xml方式（编写.xml文件放在resources下且要在application.yml中配置localtion等）
    Users selectOneUser (Users u);
    //List<User> queryUserList();
    @Insert(value = "insert into users values (#{id},#{name},#{email},#{username},#{password},#{phone},#{createdAt},#{updateAt})")
    void insertUser(Users U);
}
