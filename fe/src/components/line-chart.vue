<template>
    <v-chart :option="option"/>
</template>

<script setup>
import { ref } from 'vue';
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
const chartsData = ref([10, 8, 2, 13, 11, 20, 13]);
const xAxis = ref(['2023-1-1', '2023-1-2', '2023-1-3', '2023-1-4', '2023-1-5', '2023-1-6', '2023-1-7']);
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

</script>

<style scoped lang="less"></style>
