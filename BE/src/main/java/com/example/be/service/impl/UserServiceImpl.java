package com.example.be.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.be.BeApplication;
import com.example.be.common.Result;
import com.example.be.entity.User;
import com.example.be.mapper.UserMapper;
import com.example.be.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.example.be.BeApplication.tokenMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangwhh
 * @since 2023-12-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public Map<String, Object> login(User loginUser) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, loginUser.getUserName());
        wrapper.eq(User::getPassword, loginUser.getPassword());
        User user = this.baseMapper.selectOne(wrapper);
        if(user != null){
            String key = "user" + UUID.randomUUID();
            //redisTemplate.opsForValue().set(key, user);
            Map<String, Object> data = new HashMap<>();
            tokenMap.put(key, user.getUserId());
            data.put("token", key);
            return data;
        }
        return null;
    }

    @Override
    public Result<String> register(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, user.getEmail());
        User user1 = this.baseMapper.selectOne(wrapper);
        if(user1 != null){
            return Result.error(20002, "该邮箱已被注册");
        }
        this.baseMapper.insert(user);
        return Result.success("注册成功");
    }

    @Override
    public Result<String> updateUser(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, user.getEmail());
        User user1 = this.baseMapper.selectOne(wrapper);
        if(user1 != null && !Objects.equals(user1.getUserId(), user.getUserId())){
            return Result.error(20004, "该邮箱已被注册");
        }
        this.baseMapper.updateById(user);
        return Result.success("修改成功");
    }
}
