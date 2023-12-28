package com.example.be.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.be.common.Result;
import com.example.be.entity.Device;
import com.example.be.entity.Message;
import com.example.be.mapper.MessageMapper;
import com.example.be.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangwhh
 * @since 2023-12-12
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Override
    public Result<List<Message>> getDeviceMessage(Integer deviceId) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getDeviceId, deviceId);
        List<Message> list = this.baseMapper.selectList(wrapper);
        return Result.success(list);
    }

    @Override
    public void insertMessage(Message message) {
        this.baseMapper.insert(message);
        this.baseMapper.updateDevice(message.getDeviceId(), message.getValue()/100.0, message.getLng(), message.getLat());
    }

    @Override
    public Result<List<Map<String, String>>> getDeviceLocation(Integer ownerId) {
        List<Device> devices = this.baseMapper.selectDevice(ownerId);
        List<Map<String, String>> ret = new ArrayList<>();
        if(devices != null){
            for (Device device : devices) {
                LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Message::getDeviceId, device.getDeviceId());
                wrapper.orderByDesc(Message::getTime);
                List<Message> list = this.baseMapper.selectList(wrapper);
                Map<String, String> map = new HashMap<>();
                map.put("device_id", device.getDeviceId().toString());
                map.put("device_name", device.getDeviceName());
                map.put("msg_cnt", list.size() + "");
                if(list.size() == 0){
                    map.put("position", "[119.9, 30.1]");
                } else {
                    List<Double>position = new ArrayList<>();
                    position.add(list.get(0).getLng());
                    position.add(list.get(0).getLat());
                    map.put("position", position.toString());
                }
                ret.add(map);
            }
        }
        return Result.success(ret);
    }

    @Override
    public Result<String> addMessage(Message message) {
        this.baseMapper.insert(message);
        return Result.success("添加成功");
    }
}
