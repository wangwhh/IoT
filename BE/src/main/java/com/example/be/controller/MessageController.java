package com.example.be.controller;

import com.example.be.common.Result;
import com.example.be.entity.Message;
import com.example.be.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
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
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private IMessageService messageService;
    @GetMapping("/device")
    public Result<List<Message>> message(@RequestHeader("Authorization") String token, @RequestParam(value = "deviceId") Integer deviceId){
        Integer userId = tokenMap.get(token);
        if(userId == null){
            return Result.error(20003, "登录信息无效，请重新登录");
        }
        return messageService.getDeviceMessage(deviceId);
    }

    @GetMapping("/location")
    public Result<List<Map<String, String>>> location(@RequestHeader("Authorization") String token){
        Integer userId = tokenMap.get(token);
        if(userId == null){
            return Result.error(20003, "登录信息无效，请重新登录");
        }
        return messageService.getDeviceLocation(userId);
    }

}
