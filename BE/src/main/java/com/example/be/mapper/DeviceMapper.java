package com.example.be.mapper;

import com.example.be.entity.Device;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    @Insert("insert into message (device_id, data, state, time, value)" +
            "values (#{deviceId}, #{data}, #{state}, #{time}, #{value})")
    void insertMessage(Integer deviceId, String data, Integer state, String time, Integer value);

    @Delete("delete from message where device_id = #{deviceId}")
    void deleteMessage(Integer deviceId);

    // 查询设备消息
    @Select("select count(*) from message where device_id = #{deviceId} order by time desc")
    Integer countDeviceMessage(Integer deviceId);

    @Select("select count(*) from message where device_id = #{deviceId} and Date(time) = #{date}")
    Integer countDateMessage(Integer deviceId, String date);
}
