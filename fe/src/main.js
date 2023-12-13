import { createApp } from 'vue'
import ArcoVue from '@arco-design/web-vue';
import App from './App.vue';
import ArcoVueIcon from '@arco-design/web-vue/es/icon';
import '@arco-design/web-vue/dist/arco.css';
import router from './router';
// import { Notification } from '@arco-design/web-vue';
const app = createApp(App);
// Notification._context = app._context;

app.use(ArcoVue);
app.use(router);
app.use(ArcoVueIcon);

// app.$route = router;
app.mount('#app');
