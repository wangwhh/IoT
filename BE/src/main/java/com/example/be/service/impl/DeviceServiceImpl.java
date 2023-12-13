package com.example.be.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.be.common.Result;
import com.example.be.entity.Device;
import com.example.be.mapper.DeviceMapper;
import com.example.be.service.IDeviceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangwhh
 * @since 2023-12-12
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

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

        return Result.success("添加成功", device.getDeviceId());
    }

    @Override
    public Result<String> updateDevice(Device device) {
        this.baseMapper.updateById(device);
        return Result.success("更新成功");
    }

    @Override
    public Result<String> deleteDevice(Device device) {
        this.baseMapper.deleteById(device.getDeviceId());
        return Result.success("删除成功");
    }

}
