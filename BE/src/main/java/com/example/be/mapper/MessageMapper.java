package com.example.be.mapper;

import com.example.be.entity.Device;
import com.example.be.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangwhh
 * @since 2023-12-12
 */


@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    @Select("select * from device where owner_id = #{ownerId}")
    List<Device> selectDevice(Integer ownerId);

}
