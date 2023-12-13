<script setup>
import './assets/css/global.css'
import Menu from "@/components/menu.vue";

</script>

<template>
    <a-layout style="height: 100%;">
        <a-layout-header style="height: 7%; background: rgba(255,255,255,0)">
            <img src="@/assets/logo.png" alt="" style="height: 50px; position: relative; top: 15%; left: 1%">
        </a-layout-header>
        <a-layout-content>
            <a-layout style="height: 100%">
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
