<template>
    <p style="margin-top: 2%; margin-left: 3%; margin-bottom: 3%; font-size: 26px"> 在地图上查找你的设备</p>
    <a-input-search style="width:300px; margin-left: 5%" placeholder="请输入设备编号" search-button>
        <template #button-icon>
            <icon-search/>
        </template>
        <template #button-default>
            搜索
        </template>
    </a-input-search>
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