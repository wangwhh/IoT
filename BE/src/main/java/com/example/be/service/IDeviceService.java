package com.example.be.service;

import com.example.be.common.Result;
import com.example.be.entity.Device;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangwhh
 * @since 2023-12-12
 */
public interface IDeviceService extends IService<Device> {

    Result<List<Device>> getDeviceList(Integer userId);

    Result<Integer> addDevice(Device device);

    Result<String> updateDevice(Device device);

    Result<String> deleteDevice(Device device);
}
