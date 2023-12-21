<template>
    <div class="menu">
        <a-menu
            :style="{ height: '100%'}"
            :default-selected-keys="['0']"
        >
            <router-link to="/home">
                <a-menu-item key="0" >
                    <template #icon><icon-apps/></template>
                    首页
                </a-menu-item>
            </router-link>
            <a-sub-menu key="1">
                <template #icon><icon-common /></template>
                <template #title>设备管理</template>
                <router-link to="/devices">
                    <a-menu-item key="1_0">设备列表</a-menu-item>
                </router-link>
                <router-link to="/map">
                    <a-menu-item key="1_1">设备地图</a-menu-item>
                </router-link>
                <router-link to="/track">
                    <a-menu-item key="1_2">设备轨迹</a-menu-item>
                </router-link>
            </a-sub-menu>
            <router-link to="user">
                <a-menu-item key="2">
                    <template #icon><icon-user/></template>
                    个人中心
                </a-menu-item>
            </router-link>
        </a-menu>
    </div>
</template>
<script>
import { ref } from 'vue';
import { Message } from '@arco-design/web-vue';
import {
    IconMenuFold,
    IconMenuUnfold,
    IconApps,
    IconBug,
    IconBulb,
} from '@arco-design/web-vue/es/icon';

export default {
    components: {
        IconMenuFold,
        IconMenuUnfold,
        IconApps,
        IconBug,
        IconBulb,
    },
    setup() {
        return {

        };
    },

};
</script>
<style scoped>
.menu {
    box-sizing: border-box;
    width: 100%;
    height: 100%;
    background-color: var(--color-neutral-2);
}
</style>
