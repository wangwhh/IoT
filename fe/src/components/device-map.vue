<template>
  <div id="map"></div>
</template>

<script>
import {ref} from "vue";
import {shallowRef} from "@vue/reactivity";
import AMapLoader from '@amap/amap-jsapi-loader';

export default {
    name: "device-map",
    setup() {
        const locationData = ref([{
            device_id: 1,
            position: [116.39, 39.9],
            state: 1,
            type: 'light'
        }, {
            device_id: 2,
            position: [116.25, 39.85],
            state: 1,
            type: 'HA'
        }, {
            device_id: 3,
            position: [116.6, 39.9],
            state: 1,
            type: 'HA'
        }, {
            device_id: 4,
            position: [116.7, 39.77],
            state: 1,
            type: 'HA'
        }, {
            device_id: 5,
            position: [116.8, 40],
            state: 1,
            type: 'EC'
        }, {
            device_id: 6,
            position: [116.42, 40.05],
            state: 1,
            type: 'Secure'
        }, {
            device_id: 7,
            position: [116.38, 40.2],
            state: 1,
            type: 'Secure'
        }])
        const map = shallowRef(null);

        function initMap() {
            AMapLoader.load({
                key: "e3c2518eaa46199822ec78260a887817",
                version: "2.0",
                plugins: [''],
            }).then((AMap) => {
                let icon = new AMap.Icon({
                    size: new AMap.Size(40, 40),
                    image: "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
                    imageSize: new AMap.Size(35, 35)
                });
                this.map = new AMap.Map('map', {
                    zoom: 10,
                    center: [116.39, 39.9],
                    resizeEnable: true,
                });
                for (let i = 0; i < locationData.value.length; i++) {
                    let marker = new AMap.Marker({
                        icon: this.icon,
                        position: locationData.value[i].position,
                        offset: new AMap.Pixel(-13, -30)
                    });
                    //marker.on('click', markerClick);
                    marker.setMap(this.map);
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