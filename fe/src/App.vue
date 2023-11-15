<script setup>
import './assets/css/global.css'
import Menu from "@/components/menu.vue";
</script>

<template>
    <a-layout style="height: 100%;">
        <a-layout-header style="height: 5%">
            <img src="@/assets/logo.svg" alt="" style="width: 40px; position: relative; top: 20%; left: 1%">
            <a-typography-text style="position: relative; left: 2%; font-size: 120%; font-family: 'Microsoft YaHei UI'">Internet of Things - 物联网管理系统</a-typography-text>
        </a-layout-header>
        <a-layout-content>
            <a-layout style="height: 99%">
                <a-layout-sider v-if="!$route.meta.isLogin" collapsible breakpoint="xl">
                    <Menu/>
                </a-layout-sider>
                <a-layout-content>
                    <router-view />
                </a-layout-content>
            </a-layout>
        </a-layout-content>

    </a-layout>
</template>

<style scoped>

</style>
