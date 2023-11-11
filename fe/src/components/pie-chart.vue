<template>
    <v-chart :option="option"/>
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
import {ref} from 'vue';

use([
    CanvasRenderer,
    PieChart,
    TitleComponent,
    TooltipComponent,
    LegendComponent,
]);


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
        data: ['智能灯光', '智能冰箱', '扫地机器人', '智能空调', '智能电动车'],
    },
    series: [
        {
            name: '设备消息数',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: [
                {value: 335, name: '智能灯光'},
                {value: 310, name: '智能冰箱'},
                {value: 234, name: '扫地机器人'},
                {value: 135, name: '智能空调'},
                {value: 1548, name: '智能电动车'},
            ],
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
</script>

<style scoped>

</style>
