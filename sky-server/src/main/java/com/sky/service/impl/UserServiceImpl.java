package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.pojo.GroupCountPOJO;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    //微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    /**
     * 微信用户登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());

        // 判断openid是否为空，如果为空，表示登录失败，抛出业务异常
        if (openid == null) {
            throw  new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        User user = userMapper.getOpenid(openid);

        if(user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        return user;
    }

    @Override
    public UserReportVO countByMap(LocalDate begin, LocalDate end) {
        Map<LocalDate, Integer> map = new LinkedHashMap<>();
        LocalDate _begin = begin;
        while (!_begin.equals(end)) {
            map.put(_begin, 0);
            _begin = _begin.plusDays(1);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("beginTime", begin);
        params.put("endTime", end);
        List<GroupCountPOJO> addList = userMapper.groupCount(params);
        for (GroupCountPOJO groupCountPOJO : addList) {
            map.put(groupCountPOJO.getDate(), groupCountPOJO.getCount());
        }

        List<LocalDate> dateList = new ArrayList<>();
        List<Integer> addUserList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();
        Integer totalUserNum = 0;
        for (LocalDate localDate : map.keySet()) {
            dateList.add(localDate);
            addUserList.add(map.get(localDate));
            totalUserNum += map.get(localDate);
            totalUserList.add(totalUserNum);
        }


        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .newUserList(StringUtils.join(addUserList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .build();
    }

    /**
     * 调用微信接口服务，获取微信用户的openid
     * @param code
     * @return
     */
    private String getOpenid(String code) {

        HashMap<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, map);

        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;

    }
}
