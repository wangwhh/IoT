<template>
    <p style="margin-top: 2%; margin-left: 3%; margin-bottom: 3%; font-size: 26px"> 在地图上查看你的设备</p>
    <a-divider style="margin-top: 3%; margin-bottom: 3%"><icon-star /></a-divider>
    <device-map/>
</template>

<script>
import DeviceMap from "@/components/device-map.vue";
export default {
    name: "MapView",
    components: {DeviceMap},
}
</script>

<style scoped>

</style>