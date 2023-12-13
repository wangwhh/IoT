package com.example.be.controller;

import com.example.be.common.Result;
import com.example.be.entity.Device;
import com.example.be.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

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
}
