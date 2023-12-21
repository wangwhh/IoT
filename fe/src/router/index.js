import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '@/views/HomeView.vue';
import LoginView from "@/views/LoginView.vue";
import DeviceView from "@/views/DeviceView.vue";
import MapView from "@/views/MapView.vue";
import UserView from "@/views/UserView.vue";
import TrackView from "@/views/TrackView.vue";

const routes = [
    {
        path: '/',
        redirect: 'login',
    },
    {
        path: '/login',
        name: 'login',
        component: LoginView,
        meta: {
            isLogin: true,
        }

    },
    {
        path: '/home',
        name: 'home',
        component: HomeView,
    },
    {
        path: '/devices',
        name: 'devices',
        component: DeviceView
    },
    {
        path: '/map',
        name: 'map',
        component: MapView
    },
    {
        path: '/track',
        name: 'track',
        component: TrackView
    },
    {
        path: '/user',
        name: 'user',
        component: UserView
    }
]

const router = new createRouter({
    history: createWebHistory(),
    routes,
})

export default router;