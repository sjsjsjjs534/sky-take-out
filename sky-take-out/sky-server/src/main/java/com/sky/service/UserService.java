package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-10-25  21:17
 * @Description: TODO
 * @Version: 1.0
 */
public interface UserService {
    public User wxLogin(UserLoginDTO userLoginDTO);
}
