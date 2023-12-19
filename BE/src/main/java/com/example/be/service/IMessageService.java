package com.example.be.service;

import com.example.be.common.Result;
import com.example.be.entity.Message;
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
public interface IMessageService extends IService<Message> {

    Result<List<Message>> getDeviceMessage(Integer deviceId);

    void insertMessage(Message message);
}
