package com.example.be.controller;

import ch.qos.logback.core.joran.sanity.Pair;
import com.example.be.common.Result;
import com.example.be.entity.Device;
import com.example.be.entity.Message;
import com.example.be.service.IDeviceService;
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
 * @since 2023-12-14
 */
@Controller
@CrossOrigin
@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private IDeviceService deviceService;

    @GetMapping("/list")
    public Result<List<Device>> list(@RequestHeader("Authorization") String token){
        Integer userId = tokenMap.get(token);
        if(userId == null){
            return Result.error(20003, "登录信息无效，请重新登录");
        }
        return deviceService.getDeviceList(userId);
    }

    @PostMapping("/add")
    public Result<Integer> add(@RequestHeader("Authorization") String token, @RequestBody Device device){
        Integer userId = tokenMap.get(token);
        if(userId == null){
            return Result.error(20003, "登录信息无效，请重新登录");
        }
        device.setOwnerId(userId);
        return deviceService.addDevice(device);
    }

    @PostMapping("/update")
    public Result<String> update(@RequestHeader("Authorization") String token, @RequestBody Device device){
        Integer userId = tokenMap.get(token);
        if(userId == null || !userId.equals(device.getOwnerId())){
            return Result.error(20003, "登录信息无效，请重新登录");
        }
        return deviceService.updateDevice(device);
    }

    @PostMapping("/delete")
    public Result<String> delete(@RequestHeader("Authorization") String token, @RequestBody Device device){
        Integer userId = tokenMap.get(token);
        if(userId == null || !userId.equals(device.getOwnerId())){
            return Result.error(20003, "登录信息无效，请重新登录");
        }
        return deviceService.deleteDevice(device);
    }

    @GetMapping("/count")
    public Result<Map<String, Integer>> count(@RequestHeader("Authorization") String token){
        Integer userId = tokenMap.get(token);
        if(userId == null){
            return Result.error(20003, "登录信息无效，请重新登录");
        }
        return Result.success(deviceService.countDevice(userId));
    }

    @GetMapping("/device_msg_cnt")
    public Result<List<Map<String, String>>> countDeviceMessage(@RequestHeader("Authorization") String token){
        Integer userId = tokenMap.get(token);
        if(userId == null){
            return Result.error(20003, "登录信息无效，请重新登录");
        }
        return Result.success(deviceService.countDeviceMessage(userId));
    }

    @GetMapping("/date_msg_cnt")
    public Result<List<Map<String, String>>> countDateMessage(@RequestHeader("Authorization") String token){
        Integer userId = tokenMap.get(token);
        if(userId == null){
            return Result.error(20003, "登录信息无效，请重新登录");
        }
        return Result.success(deviceService.countDateMessage(userId));
    }
}
