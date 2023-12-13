package com.example.be.controller;

import com.example.be.common.Result;
import com.example.be.entity.User;
import com.example.be.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.Map;

import static com.example.be.BeApplication.tokenMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wangwhh
 * @since 2023-12-12
 */
@Controller
@CrossOrigin
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user){
        Map<String, Object> data = userService.login(user);
        if(data == null){
            return Result.error(20001, "用户名或密码错误");
        }
        return Result.success(data);
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody User user){
        return userService.register(user);
    }

    @GetMapping("/info")
    public Result<User> info(@RequestHeader("Authorization") String token){
        Integer userId = tokenMap.get(token);
        if(userId == null){
            return Result.error(20003, "登录信息无效，请重新登录");
        }
        User user = userService.getById(userId);
        if(user == null){
            return Result.error(20003, "登录信息无效，请重新登录");
        }
        return Result.success(user);
    }

    @PostMapping("/update")
    public Result<String> update(@RequestHeader("Authorization") String token, @RequestBody User user){
        Integer userId = tokenMap.get(token);
        if(userId == null){
            return Result.error(20003, "登录信息无效，请重新登录");
        }
        user.setUserId(userId);

        return userService.updateUser(user);
    }

    @PostMapping("/logout")
    public Result<String> logout(@RequestHeader("Authorization") String token){
        tokenMap.remove(token);
        return Result.success("退出成功");
    }
}
