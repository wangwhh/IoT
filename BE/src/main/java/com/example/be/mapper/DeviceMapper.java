package com.example.be.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.be.entity.Device;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.be.entity.Message;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangwhh
 * @since 2023-12-14
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {
    @Insert("insert into message (device_id, data, state, time) " +
            "values (#{deviceId}, #{data}, #{state}, #{time})")
    void insertMessage(Integer deviceId, String data, Integer state, String time);

    @Delete("delete from message where device_id = #{deviceId}")
    void deleteMessage(Integer deviceId);

    // 查询设备消息
    @Select("select * from message where device_id = #{deviceId} order by time desc")
    List<Message> getMessage(Integer deviceId);

    @Select("select count(*) from message where device_id = #{deviceId} and time like #{date}")
    Integer countDateMessage(Integer deviceId, String date);
}
