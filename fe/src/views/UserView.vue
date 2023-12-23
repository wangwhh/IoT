<template>
  <a-layout>
      <a-layout-sider class="user-sider" width="28%">
          <a-avatar :size="200" style="margin-top: 5%">W</a-avatar>
          <a-button style="width: 200px; margin-top: 8%" type="primary" @click="handleEditClicked">编辑信息</a-button>
          <a-button style="width: 200px; margin-top: 8%" @click="logout()">退出登录</a-button>
      </a-layout-sider>
      <a-layout-content style="padding: 50px">
          <a-descriptions style="margin-top: 20px; margin-left: 5%" :data="user_info" size="large" title="个人信息" :column="1"/>
          <a-divider style="margin-top: 3%; margin-bottom: 3%;"><icon-star /></a-divider>
      </a-layout-content>
  </a-layout>

    <a-modal v-model:visible="edit_form_visible" title="编辑个人信息" @cancel="handleCancel" @before-ok="handleBeforeOk">
        <a-form :model="edit_form">
            <a-form-item field="name" label="用户名">
                <a-input v-model="edit_form.name" :error=errorStatus.name />
            </a-form-item>
            <a-form-item field="email" label="邮箱">
                <a-input v-model="edit_form.email" :error=errorStatus.email />
            </a-form-item>
            <a-form-item field="phone" label="电话号码">
                <a-input v-model="edit_form.phone" />
            </a-form-item>
            <a-form-item field="address" label="地址">
                <a-input v-model="edit_form.address" />
            </a-form-item>
            <a-form-item field="description" label="个人简介">
                <a-textarea v-model="edit_form.description" />
            </a-form-item>
            <a-form-item v-if="alerts.flag">
                <a-alert type="error">{{alerts.message}}</a-alert>
            </a-form-item>
        </a-form>
    </a-modal>



</template>

<script>
import { ref, onMounted } from 'vue';
import api from "@/api/api";
import router from "@/router";
import { Notification } from '@arco-design/web-vue';

export default {
    name: "UserView",
    setup() {
        const user_info = ref();
        const edit_form_visible = ref(false);
        const edit_form = ref({
            name: '',
            email: '',
            phone: '',
            address: '',
            description: '',
        });
        const errorStatus = ref({
            email: false,
            name: false,
        });

        const alerts = ref({
            flag: false,
            message: '',
        })
        function handleEditClicked() {
            edit_form_visible.value = true;
            edit_form.value = {
                name: user_info.value[1].value,
                email: user_info.value[2].value,
                phone: user_info.value[3].value,
                address: user_info.value[4].value,
                description: user_info.value[5].value,
            }
        }
        function handleCancel() {
            edit_form_visible.value = false;
        }

        function handleBeforeOk() {
            function validEmail(email) {
                const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                return regex.test(email);
            }

            if(validEmail(edit_form.value.email) === false) {
                errorStatus.value.email = true;
                alerts.value.flag = true;
                alerts.value.message = '邮箱格式错误';
                return false;
            }else {
                alerts.value.flag = false;
                errorStatus.value.email = false;
            }

            if(edit_form.value.name.length <= 6) {
                errorStatus.value.name = true;
                alerts.value.flag = true;
                alerts.value.message = '用户名长度不得小于6位';
                return false;
            }else{
                alerts.value.flag = false;
                errorStatus.value.name = false;
            }

            api.post('/user/update', {
                userName: edit_form.value.name,
                email: edit_form.value.email,
                phone: edit_form.value.phone,
                address: edit_form.value.address,
                description: edit_form.value.description,
            }).then((res) => {
                if (res.data.code === 10000) {
                    Notification.success({
                        title: '修改成功',
                        content: '个人信息已更新',
                    });
                    fetchUserInfo();
                } else {
                    if (res.data.code === 20003) {
                        Notification.error({
                            title: '登录信息无效',
                            content: '请先登录',
                        });
                        router.push('/login');
                    } else {
                        Notification.error({
                            title: '修改失败',
                            content: res.data.message,
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
            return true;
        }

        async function fetchUserInfo() {
            await api.get('/user/info', {}).then((res) => {
                if (res.data.code === 10000) {
                    user_info.value =  [{
                        label: '用户ID',
                        value: res.data.data.userId,
                    }, {
                        label: '用户名',
                        value: res.data.data.userName,
                    }, {
                        label: '邮箱',
                        value: res.data.data.email,
                    }, {
                        label: '电话号码',
                        value: res.data.data.phone,
                    }, {
                        label: '地址',
                        value: res.data.data.address,
                    }, {
                        label: '个人简介',
                        value: res.data.data.description,
                    }]
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


        function logout() {
            api.post('/user/logout', {}).then((res) => {
                if(res.data.code === 10000) {
                    Notification.success({
                        title: '退出成功',
                        content: '欢迎下次再来',
                    });
                    sessionStorage.clear();
                    router.push('/login');
                } else {
                    Notification.error({
                        title: '退出失败',
                        content: '请稍后再试',
                    });
                }
            })
        }

        onMounted(() => {
            fetchUserInfo(); // 组件挂载后获取用户信息
        });
        return {
            user_info, edit_form_visible, edit_form, errorStatus, alerts,
            logout, handleCancel, handleBeforeOk, handleEditClicked
        }
    },
}
</script>

<style scoped>
.user-sider {
    box-shadow: 0 0 0 #ffffff;
    padding: 50px;

}
:deep(.arco-layout-sider-children) {
    display: flex;
    flex-direction: column;
    align-items: center;
}
</style>