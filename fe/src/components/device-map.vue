<template>
  <div id="map"></div>
</template>

<script>
import {ref} from "vue";
import {shallowRef} from "@vue/reactivity";
import AMapLoader from '@amap/amap-jsapi-loader';
import marker_img from '@/assets/marker.png'

export default {
    name: "device-map",
    setup() {
        const locationData = ref([{
            device_id: 1,
            position: [116.39, 39.9],
        }, {
            device_id: 2,
            position: [116.25, 39.85],
        }, {
            device_id: 3,
            position: [116.6, 39.9],
        }, {
            device_id: 4,
            position: [116.7, 39.77],
        }, {
            device_id: 5,
            position: [116.8, 40],
        }, {
            device_id: 6,
            position: [116.42, 40.05],
        }, {
            device_id: 7,
            position: [116.38, 40.2],
        }])
        let map = shallowRef(null);
        const hoverDeviceId = ref();


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


                const contentElement = document.createElement('div');
                const infoWindow = new AMap.InfoWindow({
                    isCustom: true,  //使用自定义窗体
                    content: contentElement,
                    offset: new AMap.Pixel(15, -30),
                });

                const openMarkerBox = function (e) {
                    hoverDeviceId.value = e.target._originOpts.device_id;
                    contentElement.className = 'echarts-tooltip-diy';
                    contentElement.innerHTML = `
                        <div style="padding: 10px">
                            <p class="tooltip-title">Title</p>
                            <div class="content-panel"><span>${hoverDeviceId.value}</span><span class="tooltip-value">xx</span></div>
                        </div>
                    `;
                    infoWindow.content= contentElement;
                    infoWindow.open(map, e.target.getPosition());
                }

                map = new AMap.Map('map', {
                    zoom: 10,
                    center: [116.39, 39.9],
                    resizeEnable: true,
                });
                for (let i = 0; i < locationData.value.length; i++) {
                    let marker = new AMap.Marker({
                        icon: icon,
                        offset: new AMap.Pixel(-13, -30),
                        position: locationData.value[i].position,
                        device_id: locationData.value[i].device_id,
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


        return {
          map, initMap
        };
    },
    methods: {

    },
    mounted() {
        this.initMap();
    }
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