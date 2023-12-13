package com.example.be.entity;

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
public class Track implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double longitude;

    private Double latitude;

    private LocalDateTime time;

    private Integer deviceId;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "Track{" +
            "longitude = " + longitude +
            ", latitude = " + latitude +
            ", time = " + time +
            ", deviceId = " + deviceId +
        "}";
    }
}
