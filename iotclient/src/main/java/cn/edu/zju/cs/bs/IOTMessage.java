package cn.edu.zju.cs.bs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IOTMessage {
    //设备ID
    private Integer deviceId;
    //上报信息
    private String data;
    //设备数据
    private int value;
    //是否告警，0-正常，1-告警
    private int state;
    //设备位置，经度
    private double lng;
    //设备位置，纬度
    private double lat;
    //上报时间，ms
    private String time;
}
