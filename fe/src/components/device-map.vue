<template>
  <div id="map"></div>
</template>

<script>
import {ref, onMounted} from "vue";
import {shallowRef} from "@vue/reactivity";
import AMapLoader from '@amap/amap-jsapi-loader';
import marker_img from '@/assets/marker.png'
import api from "@/api/api";
import router from "@/router";
import {Notification} from "@arco-design/web-vue";

export default {
    name: "device-map",
    setup() {
        const locationData = ref([])
        let map = shallowRef(null);
        const hover_device_ind = ref();

        async function fetchLocationData() {
            await api.get('/message/location').then((res) => {
                if (res.data.code === 10000) {
                    locationData.value = res.data.data;
                    locationData.value.forEach(item => {
                        if (item.position && typeof item.position === 'string') {
                            try {
                                item.position = JSON.parse(item.position);
                            } catch (e) {
                                console.error("Error parsing position for device", item.device_id, e);
                            }
                        }
                    });
                    console.log(locationData.value);
                } else {
                    if (res.data.code === 20003) {
                        Notification.error({
                            title: '登录信息无效',
                            content: '请先登录',
                        });
                        router.push('/login');
                    } else {
                        Notification.error({
                            title: '获取设备位置信息失败',
                            content: '请稍后再试',
                        });
                    }
                }
            }).catch((err) => {
                Notification.error({
                    title: '获取设备位置信息失败',
                    content: '请稍后再试',
                });
            });
        }

        function initMap() {
            console.log("init map")
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

                const contentElement = document.createElement('div');
                const infoWindow = new AMap.InfoWindow({
                    isCustom: true,  //使用自定义窗体
                    content: contentElement,
                    offset: new AMap.Pixel(15, -30),
                });

                const openMarkerBox = function (e) {
                    hover_device_ind.value = e.target._originOpts.device_ind;
                    contentElement.className = 'echarts-tooltip-diy';
                    contentElement.innerHTML = `
                        <div style="padding: 10px">
                            <p class="tooltip-title">${locationData.value[hover_device_ind.value].device_name}</p>
                            <div class="content-panel"><span>消息数量</span><span class="tooltip-value">${locationData.value[hover_device_ind.value].msg_cnt}</span></div>
                        </div>
                    `;
                    infoWindow.content= contentElement;
                    infoWindow.open(map, e.target.getPosition());
                }

                map = new AMap.Map('map', {
                    zoom: 10,
                    center: [120.2, 30.3],
                    resizeEnable: true,
                });
                for (let i = 0; i < locationData.value.length; i++) {
                    let marker = new AMap.Marker({
                        icon: icon,
                        offset: new AMap.Pixel(-13, -30),
                        position: locationData.value[i].position,
                        device_ind: i,
                    });
                    marker.on('mouseover', openMarkerBox);

                    marker.on('mouseout', function (e) {
                        infoWindow.close();
                    });
                    marker.setMap(map);
                    //markers.push(marker);
                }
            }).catch(e => {
                console.log(e);
            })
        }

        onMounted(() => {
            fetchLocationData().then(initMap);
        });

        return {
          map, locationData, hover_device_ind
        };
    },
}
</script>

<style scoped>
#map {
    width: 80%;
    height: 600px;
    margin: auto;
    padding: 20px;
    border: 20px solid #CBD9FFE5;
    box-shadow: 6px 0px 20px rgba(34, 87, 188, 0.1);
    border-radius: 8px;
}
</style>