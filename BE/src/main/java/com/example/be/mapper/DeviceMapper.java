package com.example.be.mapper;

import com.example.be.entity.Device;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangwhh
 * @since 2023-12-12
 */

@Mapper
public interface DeviceMapper extends BaseMapper<Device> {
    @Insert("insert into message (device_id, data, state, time) " +
            "values (#{deviceId}, #{data}, #{state}, #{time})")
    void insertMessage(Integer deviceId, String data, Integer state, String time);

    @Delete("delete from message where device_id = #{deviceId}")
    void deleteMessage(Integer deviceId);
}
