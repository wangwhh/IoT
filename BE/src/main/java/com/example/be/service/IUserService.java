package com.example.be.service;

import com.example.be.common.Result;
import com.example.be.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangwhh
 * @since 2023-12-12
 */
public interface IUserService extends IService<User> {


    Map<String, Object> login(User user);

    Result<String> register(User user);

    Result<String> updateUser(User user);
}
