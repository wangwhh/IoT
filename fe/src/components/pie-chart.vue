<template>
    <a-empty v-if="isEmpty" style="height: 100%; padding: 30% 0"/>
    <v-chart :option="option" v-else/>
</template>

<script setup>
import {use} from 'echarts/core';
import {CanvasRenderer} from 'echarts/renderers';
import {PieChart} from 'echarts/charts';
import {
    TitleComponent,
    TooltipComponent,
    LegendComponent,
} from 'echarts/components';
import VChart from 'vue-echarts';
import {ref, onMounted} from 'vue';
import api from "@/api/api";
import router from "@/router";

use([
    CanvasRenderer,
    PieChart,
    TitleComponent,
    TooltipComponent,
    LegendComponent,
]);

const legendData = ref([]);
const seriesData = ref([]);
const isEmpty = ref(true);
const option = ref({
    title: {
        text: '设备消息数',
        left: 'center',
    },
    tooltip: {
        trigger: 'item',
        formatter: `<div>
                <p class="tooltip-title">{b}</p>
                <div class="content-panel"><span>{c}</span><span class="tooltip-value">{d}%</span></div>
            </div>`,

        className: 'echarts-tooltip-diy',
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: legendData.value,
    },
    series: [
        {
            name: '设备消息数',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: seriesData.value,
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)',
                },
            },

            color: ['#9FD4FD', '#7BC0FC', '#57A9FB', '#3491FA', '#206CCF', '#114BA3', '#063078', '#001A4D','#E8F7FF', '#C3E7FE' ]

        },
    ],
});

async function fetchData() {
    await api.get('/device/device_msg_cnt', {}).then((res) => {
        if (res.data.code === 10000) {
            if(res.data.data.length !== 0) {
                isEmpty.value = false;
            }
            // console.log(res.data.data)
            for(let i = 0; i < res.data.data.length; i++) {
                legendData.value.push(res.data.data[i].deviceName);
                seriesData.value.push({
                    value: res.data.data[i].cnt,
                    name: res.data.data[i].deviceName,
                });
            }
            console.log(legendData.value);
            console.log(seriesData.value);
        } else {
            if (res.data.code === 20003) {
                Notification.error({
                    title: '登录信息无效',
                    content: '请先登录',
                });
                router.push('/login');
            } else {
                Notification.error({
                    title: '获取设备消息数失败',
                    content: '请稍后再试',
                });
            }
        }
    }).catch((err) => {
        Notification.error({
            title: '获取设备消息数失败',
            content: '请稍后再试',
        });
    });
}

onMounted(() => {
    fetchData();
});



</script>

<style scoped>

</style>
