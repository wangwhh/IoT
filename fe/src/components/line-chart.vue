<template>
    <v-chart :option="option"/>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { graphic } from 'echarts';
import { use } from 'echarts/core';
import {
    GridComponent,
    GraphicComponent,
    TitleComponent,
    TooltipComponent,
    LegendComponent,
} from 'echarts/components';
import { LineChart } from 'echarts/charts';
import VChart from 'vue-echarts';
import api from "@/api/api";
import router from "@/router";

use([
    LineChart,
    TitleComponent,
    TooltipComponent,
    LegendComponent,
    GridComponent,
    GraphicComponent,
]);

function graphicFactory(side) {
    return {
        type: 'text',
        bottom: '8',
        ...side,
        style: {
            text: '',
            textAlign: 'center',
            fill: '#4E5969',
            fontSize: 12,
        },
    };
}
const chartsData = ref([]);
const xAxis = ref([]);
const graphicElements = ref([
    graphicFactory({ left: '2.6%' }),
    graphicFactory({ right: 0 }),
]);
const option = ref({
    title: {
        text: '消息总数',
        left: 'center',
    },
    tooltip: {
        trigger: 'axis',
        formatter:`<div>
                <p class="tooltip-title">{b}</p>
                <div class="content-panel"><span>总数据量</span><span class="tooltip-value">{c}</span></div>
            </div>`,
        className: 'echarts-tooltip-diy',
    },
    graphic: {
        elements: graphicElements.value,
    },
    xAxis: {
        type: 'category',
        offset: 2,
        data: xAxis.value,
        boundaryGap: false,
        axisLabel: {
            color: '#4E5969',
            formatter(value, idx) {
                if (idx === 0) return '';
                if (idx === xAxis.value.length - 1) return '';
                return `${value}`;
            },
        },
        axisLine: {
            show: false,
        },
        axisTick: {
            show: false,
        },
        splitLine: {
            show: true,
            interval: (idx) => {
                if (idx === 0) return false;
                if (idx === xAxis.value.length - 1) return false;
                return true;
            },
            lineStyle: {
                color: '#E5E8EF',
            },
        },
        axisPointer: {
            show: true,
            lineStyle: {
                color: '#23ADFF',
                width: 2,
            },
        },
    },
    yAxis: {
        type: 'value',
        axisLine: {
            show: false,
        },

        splitLine: {
            show: true,
            lineStyle: {
                type: 'dashed',
                color: '#E5E8EF',
            },
        },
    },
    series: [
        {
            data: chartsData.value,
            type: 'line',
            smooth: true,
            // symbol: 'circle',
            symbolSize: 12,
            emphasis: {
                focus: 'series',
                itemStyle: {
                    borderWidth: 2,
                },
            },
            lineStyle: {
                width: 3,
                color: new graphic.LinearGradient(0, 0, 1, 0, [
                    {
                        offset: 0,
                        color: 'rgba(30, 231, 255, 1)',
                    },
                    {
                        offset: 0.5,
                        color: 'rgba(36, 154, 255, 1)',
                    },
                    {
                        offset: 1,
                        color: 'rgba(111, 66, 251, 1)',
                    },
                ]),
            },
            showSymbol: false,
            areaStyle: {
                opacity: 0.8,
                color: new graphic.LinearGradient(0, 0, 0, 1, [
                    {
                        offset: 0,
                        color: 'rgba(17, 126, 255, 0.16)',
                    },
                    {
                        offset: 1,
                        color: 'rgba(17, 128, 255, 0)',
                    },
                ]),
            },
        },
    ],

})

async function fetchData() {
    await api.get('/device/date_msg_cnt', {}).then((res) => {
        if (res.data.code === 10000) {
            console.log(res.data.data)
            for(let i = 0; i < res.data.data.length; i++) {
                chartsData.value.push(res.data.data[i].cnt);
                xAxis.value.push(res.data.data[i].date);
            }
            console.log(chartsData.value);
            console.log(xAxis.value);
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

<style scoped lang="less"></style>
