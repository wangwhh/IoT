package com.example.be.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.be.common.Result;
import com.example.be.entity.Message;
import com.example.be.mapper.MessageMapper;
import com.example.be.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    }
}
