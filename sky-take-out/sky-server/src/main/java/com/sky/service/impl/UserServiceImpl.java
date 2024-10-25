package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-10-25  21:18
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    private String WX_LOGIN="https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WeChatProperties weChatProperties;
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        //获取openId
        String openId = getOpenId(userLoginDTO.getCode());

        //如果openid为空，则说明code有问题，抛出异常
        if (openId==null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //如果存在，要看看是不是新用户，如果是新用户则创建
        //查询用户是否存在
        User user=userMapper.getByOpenid(openId);

        //如果不存在
        if (user==null){
            user=User.builder()
                    .openid(openId)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        return user;
    }


    private String getOpenId(String code){
        Map<String,String> map=new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        //发送get请求获取openid
        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;

    }
}
