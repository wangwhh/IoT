<template>
    <a-typography-title :heading="3" style="margin-left: 3%; margin-bottom: 2%"> 欢迎！Wang</a-typography-title>
    <a-divider :margin="10"><icon-star /></a-divider>
    <a-grid :cols="24" :row-gap="16" class="panel">
        <a-grid-item
                class="panel-col"
                :span="{ xs: 12, sm: 12, md: 12, lg: 12, xl: 12, xxl: 6 }"
        >
            <a-space>
                <a-avatar :size="54" class="col-avatar">
                    Device
                </a-avatar>
                <a-statistic
                        title="设备总量"
                        :value="10"
                        :precision="0"
                        :value-from="0"
                        animation
                        show-group-separator
                >
                    <template #suffix>
                        <span class="unit">个</span>
                    </template>
                </a-statistic>
            </a-space>
        </a-grid-item>
        <a-grid-item
                class="panel-col"
                :span="{ xs: 12, sm: 12, md: 12, lg: 12, xl: 12, xxl: 6 }"
        >
            <a-space>
                <a-avatar :size="54" class="col-avatar">
                    Online
                </a-avatar>
                <a-statistic
                        title="在线总量"
                        :value="8"
                        :value-from="0"
                        animation
                        show-group-separator
                >
                    <template #suffix>
                        <span class="unit"> 个 </span>
                    </template>
                </a-statistic>
            </a-space>
        </a-grid-item>

        <a-grid-item
                class="panel-col"
                :span="{ xs: 12, sm: 12, md: 12, lg: 12, xl: 12, xxl: 6 }"
        >
            <a-space>
                <a-avatar :size="54" class="col-avatar">
                    Message
                </a-avatar>
                <a-statistic
                        title="接收数据量"
                        :value="100"
                        :value-from="0"
                        animation
                        show-group-separator
                >
                    <template #suffix>
                        <span class="unit"> 条 </span>
                    </template>
                </a-statistic>
            </a-space>
        </a-grid-item>

        <a-grid-item
                class="panel-col"
                :span="{ xs: 12, sm: 12, md: 12, lg: 12, xl: 12, xxl: 6 }"
        >
            <a-space>
                <a-avatar :size="54" class="col-avatar">
                    New
                </a-avatar>
                <a-statistic
                        title="新增数据"
                        :value="15"
                        :value-from="0"
                        animation
                        show-group-separator
                >
                    <template #suffix>
                        <span class="unit"> 条 </span>
                    </template>
                </a-statistic>
            </a-space>
        </a-grid-item>
    </a-grid>
    <a-divider :margin="10"><icon-star /></a-divider>
    <a-grid :cols="2" :row-gap="16">
        <a-grid-item>
            <a-card
                class="chart-card"
                :header-style="{ paddingBottom: '0' }"
                :body-style="{ padding: '20px'}" >
                <line-chart class="chart" />
            </a-card>
        </a-grid-item>
        <a-grid-item>
            <a-card
                class="chart-card"
                :header-style="{ paddingBottom: '0' }"
                :body-style="{ padding: '20px'}" >

                <pie-chart class="chart" />
            </a-card>
        </a-grid-item>
    </a-grid>
</template>

<script>
import Menu from '../components/menu.vue'
import pieChart from "@/components/pie-chart.vue";
import lineChart from "@/components/line-chart.vue";
export default {
    name: "HomeView",
    components: {pieChart, lineChart, Menu},
    data() {
        return {

        }
    }
}

</script>

<style scoped>
.panel {
    margin-bottom: 1%;
    padding: 16px 20px 0 20px;
}
.panel-col {
    padding-left: 43px;
    border-right: 1px solid rgb(var(--gray-2));
}
.col-avatar {
    margin-right: 12px;
    background-color: #57A9FB;
}
.unit {
    margin-left: 8px;
    color: rgb(var(--gray-8));
    font-size: 12px;
}
:deep(.panel-border) {
    margin: 4px 0 0 0;
}
.chart-card {
    //width: 800px;
    height: 500px;
    margin-left: 1%;
    margin-right: 1%;
    margin-top: 2%
}
.chart {
    height: 450px;
}
</style>