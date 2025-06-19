package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 根据openid查询微信用户
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User getOpenid(String openid);

    /**
     * 新增微信用户
     * @param user
     */
    @Insert("insert into user (openid, name, phone, sex, id_number, avatar, create_time) " +
            "values(#{openid}, #{name}, #{phone}, #{sex}, #{id_number}, #{avatar}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);
}
