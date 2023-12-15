package com.example.be.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.be.common.Result;
import com.example.be.entity.Device;
import com.example.be.entity.Message;
import com.example.be.mapper.DeviceMapper;
import com.example.be.service.IDeviceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangwhh
 * @since 2023-12-14
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {
    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public Result<List<Device>> getDeviceList(Integer userId) {
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getOwnerId, userId);
        List<Device> list = this.baseMapper.selectList(wrapper);
        return Result.success(list);
    }

    @Override
    public Result<Integer> addDevice(Device device) {
        this.baseMapper.insert(device);
        deviceMapper.insertMessage(device.getDeviceId(), "添加设备"+device.getDeviceName(), 1, device.getAddDate().toString());
        return Result.success("添加成功", device.getDeviceId());
    }

    @Override
    public Result<String> updateDevice(Device device) {
        this.baseMapper.updateById(device);
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        deviceMapper.insertMessage(device.getDeviceId(), "修改设备信息", 1, dateFormat.format(date));
        return Result.success("更新成功");
    }

    @Override
    public Result<String> deleteDevice(Device device) {
        this.baseMapper.deleteById(device.getDeviceId());
        deviceMapper.deleteMessage(device.getDeviceId());
        return Result.success("删除成功");
    }

    @Override
    public Map<String, Integer> countDevice(Integer userId) {
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getOwnerId, userId);
        List<Device> list = this.baseMapper.selectList(wrapper);
        int device_cnt = list.size();
        int online_cnt = 0;
        int total_msg = 0;
        for(Device device : list) {
            if(device.getSoc() != 0) {
                online_cnt++;
            }
            List<Message> list1 = deviceMapper.getMessage(device.getDeviceId());
            total_msg += list1.size();
        }
        return Map.of("device_cnt", device_cnt, "online_cnt", online_cnt, "total_msg", total_msg);
    }

    @Override
    public Result<List<Message>> getDeviceMessage(Integer deviceId) {
        List<Message> list = deviceMapper.getMessage(deviceId);
        return Result.success(list);
    }

    @Override
    public Map<String, Integer> countDeviceMessage(Integer userId) {
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getOwnerId, userId);
        List<Device> list = this.baseMapper.selectList(wrapper);
        Map<String, Integer> ret = new HashMap<>();
        for(Device device : list) {
            int cnt = getDeviceMessage(device.getDeviceId()).getData().size();
            ret.put(device.getDeviceName(), cnt);
        }
        return ret;
    }

    @Override
    public Map<String, Integer> countDateMessage(Integer userId) {
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Device::getOwnerId, userId);
        List<Device> list = this.baseMapper.selectList(wrapper);
        Map<String, Integer> ret = new HashMap<>();
        LocalDateTime localDateTime = LocalDateTime.now();

        // 统计近一周每天的消息数量
        for(int i = 0; i < 7; i++) {
            int cnt = 0;
            for(Device device : list) {
                cnt += deviceMapper.countDateMessage(device.getDeviceId(), localDateTime.minusDays(i).toString());
            }
            ret.put(localDateTime.minusDays(i).toString(), cnt);
        }

        return ret;
    }


}
