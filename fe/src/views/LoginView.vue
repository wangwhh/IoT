<template>
    <div id="building">
        <div>
            <a-card v-if="state === 'login'" class="login-card" title="登录" >
                <a-input v-model="loginForm.name" placeholder="用户名" style="width: 80%; margin-top: 7%; margin-bottom: 8%">
                    <template #prefix>
                        <icon-user />
                    </template>
                </a-input>
                <a-input type="password" v-model="loginForm.pwd" placeholder="请输入密码" style="width: 80%; margin-bottom: 8%">
                    <template #prefix>
                        <icon-lock />
                    </template>
                </a-input>
                <a-checkbox style="width: 80%; margin-bottom: 8%"> 记住密码 </a-checkbox>
                <a-button type="primary" style="width: 80%; margin-bottom: 5%" @click="this.handleLogin">登录</a-button>
                <a-button type="text" style="width: 80%; margin-bottom: 5%" @click="this.switchToRegister">注册账号</a-button>
            </a-card>

            <a-card v-else-if="state === 'register'" class="login-card" title="注册" hoverable>
                <template #actions>
                    <a-button type="text" @click="switchToLogin">
                        <icon-double-left />
                        返回
                    </a-button>
                </template>
                <a-input v-model="registerForm.email" placeholder="邮箱" style="width: 80%; margin-top: 7%; margin-bottom: 5%" :error=this.errorStatus.email>
                    <template #prefix>
                        <icon-email />
                    </template>
                </a-input>
                <a-input v-model="registerForm.name" placeholder="用户名" style="width: 80%; margin-bottom: 5%" :error=this.errorStatus.name>
                    <template #prefix>
                        <icon-user />
                    </template>
                </a-input>
                <a-input-password v-model="registerForm.pwd" placeholder="请输入密码" style="width: 80%; margin-bottom: 5%" :error=this.errorStatus.pwd>
                    <template #prefix>
                        <icon-lock />
                    </template>
                </a-input-password>
                <a-input-password v-model="registerForm.pwd2" placeholder="请再次输入密码" style="width: 80%; margin-bottom: 5%" :error=this.errorStatus.pwd2>
                    <template #prefix>
                        <icon-lock />
                    </template>
                </a-input-password>
                <a-button type="primary" style="width: 80%; margin-bottom: 5%" @click="this.handleRegister">注册</a-button>
                <a-alert v-if="this.alerts.flag" type="error" style="width: 80%; margin: auto">{{this.alerts.message}}</a-alert>
            </a-card>
        </div>
    </div>
</template>

<script>

import router from "@/router";
import api from "@/api/api";
import md5 from 'js-md5';
import { Notification } from '@arco-design/web-vue';

export default {
    data() {
        return {
            state: 'login',
            loginForm: {
                name: '',
                pwd: '',
            },
            registerForm: {
                email: '',
                name: '',
                pwd: '',
                pwd2: '',
            },
            errorStatus: {
                email: false,
                name: false,
                pwd: false,
                pwd2: false,
            },
            alerts: {
                flag: false,
                message: ''
            }
        };
    },
    created() {

    },
    methods: {
        switchToRegister() {
            this.state = 'register';
        },
        switchToLogin() {
            this.state = 'login';
        },
        handleLogin() {
            api.post('/user/login', {
                    userName: this.loginForm.name,
                    password: md5(this.loginForm.pwd),
                }).then(res => {
                // console.log(res);
                if(res.data.code === 10000) {
                    Notification.success({
                        title: '登录成功',
                        content: '欢迎回来',
                    });
                    sessionStorage.setItem('token', res.data.data.token);
                    api.defaults.headers.common['Authorization'] = res.data.data.token;
                    router.push('/home');
                }else {
                    // console.log(res.data)
                    Notification.error({
                        title: '登录失败',
                        content: res.data.message,
                    });
                }
            }).catch(err => {
                console.log(err);

            })
            //router.push('/home');
        },
        handleRegister() {
            function validEmail(email) {
                const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                return regex.test(email);
            }

            if(validEmail(this.registerForm.email) === false) {
                this.errorStatus.email = true;
                this.alerts.flag = true;
                this.alerts.message = '邮箱格式错误';
                return;
            }else {
                this.alerts.flag = false;
                this.errorStatus.email = false;
            }
            if(this.registerForm.name.length <= 6) {
                this.errorStatus.name = true;
                this.alerts.flag = true;
                this.alerts.message = '用户名长度不得小于6位';
                return;
            }else{
                this.alerts.flag = false;
                this.errorStatus.name = false;
            }
            if(this.registerForm.pwd.length <= 6) {
                this.errorStatus.pwd = true;
                this.alerts.flag = true;
                this.alerts.message = '密码长度不得小于6位';
                return;
            }else {
                this.alerts.flag = false;
                this.errorStatus.pwd = false;
            }
            if(this.registerForm.pwd !== this.registerForm.pwd2) {
                this.errorStatus.pwd2 = true;
                this.alerts.flag = true;
                this.alerts.message = '两次密码输入不一致';
                return;
            }else{
                this.alerts.flag = false;
                this.errorStatus.pwd2 = false;
            }
            api.post('/user/register', {
                email: this.registerForm.email,
                userName: this.registerForm.name,
                password: md5(this.registerForm.pwd),
            }).then(res => {
                if(res.data.code === 10000) {
                    Notification.success({
                        title: '注册成功',
                        content: '请登录',
                    });
                    this.switchToLogin();
                }else {
                    Notification.error({
                        title: '注册失败',
                        content: res.data.message,
                    });
                }
            }).catch(err => {
                console.log(err);
            })
        },

    }
};
</script>


<style scoped>
#building {
    background-image: url('../assets/loginBG0.png');
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center;
    width: 100%;
    height: 100%;
    position: fixed;
    z-index: 0;
}
.login-card {
    position: absolute;
    top: 20%;
    right: 10%;
    width: 350px;
    height: 400px;
    text-align: center;
    box-shadow: 6px 0px 20px rgba(34, 87, 188, 0.1);
    border-radius: 15px;
    background: #F6FAFFFF;
}
</style>