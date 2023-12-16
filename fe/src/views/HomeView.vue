<template>
    <a-typography-title :heading="3" style="margin-left: 3%; margin-bottom: 2%"> 欢迎！{{user_name}}</a-typography-title>
    <a-divider :margin="10"><icon-star /></a-divider>
    <a-grid :cols="4" :row-gap="16" class="panel">
        <a-grid-item
                class="panel-col"
                :span="{ xs: 4, sm: 4, md: 2, lg: 1, xl: 1, xxl: 1 }"
        >
            <a-space>
                <a-avatar :size="54" class="col-avatar">
                    Device
                </a-avatar>
                <a-statistic
                        title="设备总量"
                        :value=device_cnt
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
                :span="{ xs: 4, sm: 4, md: 2, lg: 1, xl: 1, xxl: 1 }"
        >
            <a-space>
                <a-avatar :size="54" class="col-avatar">
                    Online
                </a-avatar>
                <a-statistic
                        title="在线总量"
                        :value=online_cnt
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
                :span="{ xs: 4, sm: 4, md: 2, lg: 1, xl: 1, xxl: 1 }"
        >
            <a-space>
                <a-avatar :size="54" class="col-avatar">
                    Message
                </a-avatar>
                <a-statistic
                        title="接收数据量"
                        :value=msg_cnt
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
                :span="{ xs: 4, sm: 4, md: 2, lg: 1, xl: 1, xxl: 1 }"
        >
            <a-space>
                <a-avatar :size="54" class="col-avatar">
                    New
                </a-avatar>
                <a-statistic
                        title="新增数据"
                        :value=new_cnt
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
        <a-grid-item :span="{ xs: 2, sm: 2, md: 2, lg: 1, xl: 1, xxl: 1 }">
            <a-card
                class="chart-card"
                :header-style="{ paddingBottom: '0' }"
                :body-style="{ padding: '20px'}" >
                <line-chart class="chart" />
            </a-card>
        </a-grid-item>
        <a-grid-item :span="{ xs: 2, sm: 2, md: 2, lg: 1, xl: 1, xxl: 1 }">
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
import { ref, onMounted } from 'vue';
import pieChart from "@/components/pie-chart.vue";
import lineChart from "@/components/line-chart.vue";
import api from "@/api/api";
import router from "@/router";
import { Notification } from '@arco-design/web-vue';
export default {
    name: "HomeView",
    components: {pieChart, lineChart, Menu},
    setup() {
        const user_name = ref('');
        const device_cnt = ref(0);
        const online_cnt = ref(0);
        const msg_cnt = ref(0);
        const new_cnt = ref(0);
        async function fetchUserName() {
            await api.get('/user/info', {}).then((res) => {
                if (res.data.code === 10000) {
                    user_name.value = res.data.data.userName;
                } else {
                    if (res.data.code === 20003) {
                        Notification.error({
                            title: '登录信息无效',
                            content: '请先登录',
                        });
                        router.push('/login');
                    } else {
                        Notification.error({
                            title: '获取用户信息失败',
                            content: '请稍后再试',
                        });
                    }
                }
            }).catch((err) => {
                console.log(err);
                Notification.error({
                    title: '登录信息失效',
                    content: '请重新登录',
                });
                router.push('/login');
            })
        }

        async function fetchDeviceCount() {
            await api.get('/device/count', {}).then((res) => {
                if (res.data.code === 10000) {
                    // console.log(res.data.data);
                    device_cnt.value = res.data.data.device_cnt;
                    online_cnt.value = res.data.data.online_cnt;
                    msg_cnt.value = res.data.data.total_msg;
                } else {
                    if (res.data.code === 20003) {
                        Notification.error({
                            title: '登录信息无效',
                            content: '请先登录',
                        });
                        router.push('/login');
                    } else {
                        Notification.error({
                            title: '获取用户信息失败',
                            content: '请稍后再试',
                        });
                    }
                }
            }).catch((err) => {
                console.log(err);
                Notification.error({
                    title: '登录信息失效',
                    content: '请重新登录',
                });
                router.push('/login');
            })
        }

        async function fetchNew() {
            await api.get('/device/date_msg_cnt', {}).then((res) => {
                if (res.data.code === 10000) {
                    let count = 0;
                    for(let i = 0; i < res.data.data.length; i++) {
                        count += Number(res.data.data[i].cnt);
                    }
                    console.log(count);
                    new_cnt.value = count;
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
            fetchUserName();
            fetchDeviceCount();
            fetchNew();
        })

        return {
            user_name, msg_cnt, online_cnt, device_cnt, new_cnt
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