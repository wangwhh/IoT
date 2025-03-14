package com.example.be.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangwhh
 * @since 2023-12-12
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer deviceId;

    private String data;

    private Integer state;

    private Integer value;

    private Double lng;

    private Double lat;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime time;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }



    @Override
    public String toString() {
        return "Message{" +
            "deviceId = " + deviceId +
            ", data = " + data +
            ", state = " + state +
            ", time = " + time +
            ", value = " + value +
            ", lng = " + lng +
            ", lat = " + lat +
        "}";
    }
}
