<template>
  <div id="map"></div>
</template>

<script>
import {shallowRef} from "@vue/reactivity";
import AMapLoader from '@amap/amap-jsapi-loader';
export default {
    name: "device-map",
    setup() {
        const map = shallowRef(null);
        return {
          map,
        };
    },
    methods: {
        initMap() {
            AMapLoader.load({
                key: "e3c2518eaa46199822ec78260a887817",
                version: "2.0",
                plugins: [''],
            }).then((AMap) => {
                this.map = new AMap.Map('map', {
                    zoom: 10,
                    center: [116.39, 39.9],
                    resizeEnable: true,
                });
            }).catch(e => {
                console.log(e);
            })
        }
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