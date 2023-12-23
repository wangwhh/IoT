<template>
    <a-select :default-value="select_tag" style="width: 360px; margin-left: 5%; margin-top: 2%" size="large"
              placeholder="选择设备类型" multiple  allow-clear @change="updateSelect">
        <a-option>照明</a-option>
        <a-option>家电</a-option>
        <a-option>能耗</a-option>
        <a-option>安防</a-option>
        <a-option>其他</a-option>
    </a-select>

    <a-button type="primary" style="margin-left: 2%" @click="insert_visible = true;">
        <template #icon>
            <icon-plus />
        </template>
        <template #default>添加设备</template>
    </a-button>

    <a-divider style="margin-top: 3%; margin-bottom: 3%"><icon-star /></a-divider>

    <a-grid :cols="{ xs: 1, sm: 2, md: 4, lg: 4, xl: 4, xxl: 4 }" :colGap="20" :rowGap="20" style="margin-left: 2%; margin-right: 4%">
        <a-grid-item v-for="device in selected_devices">
            <a-card class="device-card" hoverable>
                <template #actions>
                    <a-tag color="arcoblue">{{device.type}}</a-tag>
                    <a-badge dot :count="9" :max-count="10" :offset="[-1, 1]">
                        <span class="icon-hover" style="font-size: 20px" @click="openMsgList(device)"> <IconMessage /> </span>
                    </a-badge>
                    <span class="icon-hover" style="font-size: 20px" @click="openEditForm(device)"> <icon-edit /> </span>
                    <a-popconfirm content="确认要删除这个设备吗？" type="warning" @ok="handleDelete(device)">
                        <span class="icon-hover" style="font-size: 20px"> <icon-delete /> </span>
                    </a-popconfirm>

                </template>
                <template #cover>

                    <a-progress class="progress"
                                type="circle"
                                :percent="device.soc"
                                size="large"
                                :color="device.soc>0.2 ? (device.soc>0.8 ? '#23C343' : '#4080FF') : '#F53F3F'"/>

                </template>
                <a-card-meta :title="device.deviceName" :description="device.description">
                    <template #avatar>
                        <div
                            :style="{  alignItems: 'center', color: '#1D2129' }"
                        >
                            <a-typography-text style="font-size: 16px">#{{device.deviceId}}</a-typography-text>
                        </div>
                    </template>
                </a-card-meta>
            </a-card>
        </a-grid-item>
    </a-grid>

    <a-modal :visible="edit_visible" @ok="editOk" @cancel="edit_visible=false">
        <template #title>
            修改设备信息
        </template>
        <a-form :model="edit_form">
            <a-form-item field="name" label="设备名称">
                <a-input v-model="edit_form.deviceName" />
            </a-form-item>
            <a-form-item field="type" label="设备类型">
                <a-select v-model="edit_form.type" >
                    <a-option>照明</a-option>
                    <a-option>家电</a-option>
                    <a-option>能耗</a-option>
                    <a-option>安防</a-option>
                    <a-option>其他</a-option>
                </a-select>
            </a-form-item>
            <a-form-item label="设备简介">
                <a-input v-model="edit_form.description" />
            </a-form-item>
        </a-form>
    </a-modal>

    <a-modal :visible="insert_visible" @ok="insertOk" @cancel="insert_visible=false">
        <template #title>
            添加设备
        </template>
        <a-form :model="insert_form">
            <a-form-item field="name" label="设备名称">
                <a-input v-model="insert_form.deviceName" />
            </a-form-item>
            <a-form-item field="type" label="设备类型">
                <a-select v-model="insert_form.type" >
                    <a-option>照明</a-option>
                    <a-option>家电</a-option>
                    <a-option>能耗</a-option>
                    <a-option>安防</a-option>
                    <a-option>其他</a-option>
                </a-select>
            </a-form-item>
            <a-form-item filed="description" label="设备简介">
                <a-input v-model="insert_form.description" />
            </a-form-item>
        </a-form>
    </a-modal>

    <a-modal :visible="msg_visible" @ok="msg_visible=false" @cancel="msg_visible=false" hideCancel >
        <template #title>
            消息列表
        </template>
        <a-list :max-height="300" scrollbar>
            <template #header>
                {{ msg_list.device_name }}
            </template>
            <a-list-item v-for="msg in msg_list.list">
                <a-list-item-meta :title="msg.time" :description="msg.data">
                    <template #avatar>
                        <a-avatar :style="{ backgroundColor: msg.state === 1 ? '#4080FF':'#F76560' }">
                            <icon-check v-if="msg.state === 1"/>
                            <icon-exclamation v-if="msg.state === 0"/>
                        </a-avatar>
                    </template>
                </a-list-item-meta>
            </a-list-item>
        </a-list>
    </a-modal>

</template>

<script>

import {ref, onMounted} from "vue";
import api from "@/api/api";
import router from "@/router";
import {Notification} from "@arco-design/web-vue";
export default {
    name: "DeviceView",

    setup() {
        const devices = ref();

        const selected_devices = ref(devices.value);
        const select_tag = ref(['照明', '家电', '能耗', '安防', '其他']);

        const edit_visible = ref(false);
        const edit_form = ref({
            deviceId: '',
            ownerId: '',
            deviceName: '',
            type: '',
            description: '',
        });

        const insert_visible = ref(false);
        const insert_form = ref({
            deviceName: '',
            type: '',
            description: '',
            addDate: getToday(),
            soc: 0,
            msgCnt: 0,
        });

        function getToday(){
            let date = new Date();
            let year = date.getFullYear();
            let month = date.getMonth()+1;
            let day = date.getDate();
            console.log(year+'-'+month+'-'+day+' '+date.toLocaleTimeString());
            return year+'-'+month+'-'+day+' '+date.toLocaleTimeString()
        }

        const msg_visible = ref(false);
        const msg_list = ref({
            device_name: '' ,
            list: []
        });

        async function getDevices(){
            await api.get('/device/list', {}).then((res) => {
                if(res.data.code === 10000) {
                    // console.log(res.data)
                    devices.value = res.data.data;
                    selected_devices.value = devices.value;
                    // console.log(devices.value);
                } else {
                    if (res.data.code === 20003) {
                        Notification.error({
                            title: '登录信息无效',
                            content: '请先登录',
                        });
                        router.push('/login');
                    } else {
                        Notification.error({
                            title: '获取设备列表失败',
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

        function updateSelect(value){
            select_tag.value = value;
            selected_devices.value = [];
            for(let i=0; i<devices.value.length; i++){
                if(select_tag.value.indexOf(devices.value[i].type) !== -1){
                    selected_devices.value.push(devices.value[i]);
                }
            }
        }

        function editOk(){
            api.post('/device/update', edit_form.value).then((res) => {
                if (res.data.code === 10000) {
                    Notification.success({
                        title: '修改成功',
                        content: '设备信息已更新',
                    });
                    getDevices();
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
            edit_visible.value = false;
        }


        function insertOk(){
            // console.log(insert_form.value);
            api.post('device/add', insert_form.value).then((res) => {
                if (res.data.code === 10000) {
                    Notification.success({
                        title: '添加成功',
                        content: '您的设备编号为【'+res.data.data+'】，后续请使用该编号与您的设备进行绑定。',
                        closable: true,
                        duration: 0,
                    });
                    getDevices();
                } else {
                    if (res.data.code === 20003) {
                        Notification.error({
                            title: '登录信息无效',
                            content: '请先登录',
                        });
                        router.push('/login');
                    } else {
                        Notification.error({
                            title: '添加失败',
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
            insert_visible.value = false;
        }


        function openEditForm(device){
            edit_form.value = device;
            edit_visible.value = true;
        }


        async function getMsgList(device){
            await api.get('/message/device', {
                params: {
                    deviceId: device.deviceId,
                }
            }).then((res) => {
                if (res.data.code === 10000) {
                    msg_list.value.list = res.data.data;
                    console.log(msg_list.value.list.time);
                } else {
                    if (res.data.code === 20003) {
                        Notification.error({
                            title: '登录信息无效',
                            content: '请先登录',
                        });
                        // router.push('/login');
                    } else {
                        Notification.error({
                            title: '获取消息列表失败',
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
                // router.push('/login');
            })
        }

        function openMsgList(device){
            msg_visible.value = true;
            msg_list.value.device_name = device.deviceName;
            getMsgList(device);
        }

        function handleDelete(device){
            api.post('/device/delete', device).then((res) => {
                if (res.data.code === 10000) {
                    Notification.success({
                        title: '删除成功',
                        content: '设备已删除',
                    });
                    getDevices();
                } else {
                    if (res.data.code === 20003) {
                        Notification.error({
                            title: '登录信息无效',
                            content: '请先登录',
                        });
                        router.push('/login');
                    } else {
                        Notification.error({
                            title: '删除失败',
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
        }

        onMounted(() => {
            getDevices();
        })

        return{
          devices, select_tag, selected_devices, edit_visible, edit_form, insert_visible, insert_form, msg_visible, msg_list,
          updateSelect, openEditForm, editOk, insertOk, handleDelete, openMsgList
        }
    }
}
</script>

<style scoped>
.device-card{
    //height: 300px;
    border-radius: 5px;
    //box-shadow: 6px 0px 6px #d5d5d5;
}
.progress{
    text-align: center;
    margin-top: 8%;
}
</style>