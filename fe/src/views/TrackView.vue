<template>
    <p style="margin-top: 2%; margin-left: 3%; margin-bottom: 3%; font-size: 26px"> 查看设备轨迹</p>
    <a-select v-model="selected_device_id" style="width: 320px; margin-left: 100px" placeholder="选择设备...">
        <a-option v-for="device of device_list" :value="device.deviceId" :label="device.deviceName" />
    </a-select>
    <a-divider style="margin-top: 3%; margin-bottom: 3%"><icon-star /></a-divider>
    <div id="map"></div>
</template>

<script>
import marker_img from "@/assets/marker.png";
import {ref, onMounted, watch} from "vue";
import {shallowRef} from "@vue/reactivity";
import AMapLoader from '@amap/amap-jsapi-loader';
import api from "@/api/api";
import router from "@/router";
import {Notification} from "@arco-design/web-vue";

export default {
    name: "TrackView",
    setup() {
        let map = shallowRef(null);
        const device_list = ref([]);
        const selected_device_id = ref();
        const track_list = ref([]);

        async function fetchDeviceList() {
            await api.get('/device/list').then((res) => {
                if (res.data.code === 10000) {
                    device_list.value = res.data.data.filter((item) => {
                        return item.type === "能耗";
                    });
                } else {
                    if (res.data.code === 20003) {
                        Notification.error({
                            title: '登录信息无效',
                            content: '请先登录',
                        });
                        router.push('/login');
                    } else {
                        Notification.error({
                            title: '获取设备列表失败',
                            content: '请稍后再试',
                        });
                    }
                }
            }).catch((err) => {
                Notification.error({
                    title: '获取设备列表失败',
                    content: '请稍后再试',
                });
            });
        }

        async function fetchTrack() {
            await api.get('/message/device', {
                params: {
                    deviceId: selected_device_id.value,
                }
            }).then((res) => {
                if (res.data.code === 10000) {
                    track_list.value = res.data.data;
                    // console.log(track_list.value);
                } else {
                    if (res.data.code === 20003) {
                        Notification.error({
                            title: '登录信息无效',
                            content: '请先登录',
                        });
                        router.push('/login');
                    } else {
                        Notification.error({
                            title: '获取设备列表失败',
                            content: '请稍后再试',
                        });
                    }
                }
            }).catch((err) => {
                Notification.error({
                    title: '获取设备列表失败',
                    content: '请稍后再试',
                });
            });
        }

        watch(selected_device_id, (val) => {
            fetchTrack().then(initMap);
        })

        function initMap() {
            AMapLoader.load({
                key: "e3c2518eaa46199822ec78260a887817",
                version: "2.0",
                plugins: [''],
            }).then((AMap) => {
                const icon = new AMap.Icon({
                    size: new AMap.Size(40, 40),
                    image: marker_img,
                    imageSize: new AMap.Size(45, 45)
                });

                map = new AMap.Map('map', {
                    zoom: 10,
                    center: [120.2, 30.3],
                    resizeEnable: true,
                });

                if(track_list.value.length > 0) {
                    let path = [];
                    for(let track of track_list.value) {
                        path.push([track.lng, track.lat]);
                    }
                    // console.log(path)
                    let polyline = new AMap.Polyline({
                        path: path,
                        isOutline: true,
                        outlineColor: '#ffeeff',
                        borderWeight: 2,
                        strokeColor: "#3366FF",  //线颜色
                        strokeOpacity: 1,     //线透明度
                        strokeWeight: 3,      //线宽
                        strokeStyle: "solid"  //线样式
                    });

                    map.add(polyline);
                }

            }).catch(e => {
                console.log(e);
            })
        }

        onMounted(() => {
            fetchDeviceList();
            initMap();
        })

        return {
            map, device_list, selected_device_id
        };
    },
}
</script>

<style scoped>
#map {
    width: 80%;
    height: 500px;
    margin: auto;
    padding: 20px;
    border: 20px solid #CBD9FFE5;
    box-shadow: 6px 0px 20px rgba(34, 87, 188, 0.1);
    border-radius: 8px;
}
</style>