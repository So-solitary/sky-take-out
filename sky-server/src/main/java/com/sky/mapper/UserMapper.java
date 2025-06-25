package com.sky.mapper;

import com.sky.entity.User;
import com.sky.vo.UserReportVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.Map;

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
            "values(#{openid}, #{name}, #{phone}, #{sex}, #{idNumber}, #{avatar}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    /**
     * 根据id获取用户
     * @param userId
     * @return
     */
    @Select("select * from user where id = #{userId}")
    User getById(Long userId);


    /**
     * 统计新增用户数量
     * @param params
     * @return
     */
    Map<LocalDate, Integer> groupCount(Map<String, Object> params);

    /**
     * 统计当前日期用户数量
     * @param end
     * @return
     */
    Map<LocalDate, Integer> partitionCount(LocalDate end);
}
