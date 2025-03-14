package com.example.be.service;

import ch.qos.logback.core.joran.sanity.Pair;
import com.example.be.common.Result;
import com.example.be.entity.Device;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.be.entity.Message;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangwhh
 * @since 2023-12-14
 */
public interface IDeviceService extends IService<Device> {
    Result<List<Device>> getDeviceList(Integer userId);

    Result<Integer> addDevice(Device device);

    Result<String> updateDevice(Device device);

    Result<String> deleteDevice(Device device);

    Map<String, Integer> countDevice(Integer userId);

//    Result<List<Message>> getDeviceMessage(Integer deviceId);

    List<Map<String, String>> countDeviceMessage(Integer userId);

    List<Map<String, String>> countDateMessage(Integer userId);
}
