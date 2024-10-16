package com.sky.service;

import com.sky.dto.DishDTO;

/**
 * @BelongsProject: sky-take-out
 * @Author: 张宇若
 * @CreateTime: 2024-10-16  20:46
 * @Description: TODO
 * @Version: 1.0
 */
public interface DishService {
    public void saveWithFlavors(DishDTO dishDTO);
}
