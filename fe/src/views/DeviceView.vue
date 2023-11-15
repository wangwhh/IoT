<template>
    <a-select :default-value="select_tag" style="width: 360px; margin-left: 5%" size="large"
              placeholder="选择设备类型" multiple  allow-clear @change="updateSelect">
        <a-option>照明</a-option>
        <a-option>家电</a-option>
        <a-option>能耗</a-option>
        <a-option>安防</a-option>
        <a-option>其他</a-option>
    </a-select>

    <a-input-search style="width:300px; margin-left: 2%; margin-top: 2%" placeholder="查询设备" search-button>
        <template #button-icon>
            <icon-search/>
        </template>
        <template #button-default>
            搜索
        </template>
    </a-input-search>

    <a-button type="primary" style="margin-left: 2%" @click="openInsertForm">
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
                    <a-tag color="arcoblue">{{type2str(device.type)}}</a-tag>
                    <a-badge dot :count="9" :max-count="10" :offset="[-1, 1]">
                        <span class="icon-hover" style="font-size: 20px" @click="openMsgList(device)"> <IconMessage /> </span>
                    </a-badge>
                    <span class="icon-hover" style="font-size: 20px" @click="openEditForm(device)"> <icon-edit /> </span>
                </template>
                <template #cover>

                    <a-progress class="progress"
                                type="circle"
                                :percent="device.SOC"
                                size="large"
                                :color="device.SOC>0.2 ? (device.SOC>0.8 ? '#23C343' : '#4080FF') : '#F53F3F'"/>

                </template>
                <a-card-meta :title="device.name" :description="device.description">
                    <template #avatar>
                        <div
                            :style="{  alignItems: 'center', color: '#1D2129' }"
                        >
                            <a-typography-text style="font-size: 16px">#{{device.id}}</a-typography-text>
                        </div>
                    </template>
                </a-card-meta>
            </a-card>
        </a-grid-item>
    </a-grid>

    <a-modal :visible="edit_visible" @ok="editOk" @cancel="editCancel">
        <template #title>
            修改设备信息
        </template>
        <a-form :model="edit_form">
            <a-form-item field="name" label="设备名称">
                <a-input v-model="edit_form.name" />
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
        </a-form>
    </a-modal>

    <a-modal :visible="insert_visible" @ok="insertOk" @cancel="insertCancel">
        <template #title>
            添加设备
        </template>
        <a-form :model="insert_form">
            <a-form-item field="name" label="设备名称">
                <a-input v-model="insert_form.name" />
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
        </a-form>
    </a-modal>

    <a-modal :visible="msg_visible" @ok="msgOk" @cancel="msgCancel" hideCancel >
        <template #title>
            消息列表
        </template>
        <a-list :max-height="240" scrollbar>
            <template #header>
                {{ msg_list.device_name }}
            </template>
            <a-list-item v-for="msg in msg_list.list">
                <a-list-item-meta :title="msg.time" :description="msg.msg">
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

import {ref} from "vue";
export default {
    name: "DeviceView",

    setup() {
        const devices = ref([{
            name: "智能灯光", id: 1, type:'light', SOC: 1, description: '这是客厅的灯。',
            msg_list: [{
                time: "2023-11-1", msg: "灯泡寿命已过期，请及时更换。", state: 0
            }, {
                time: "2023-11-2", msg: "更换成功。", state: 1
            }, {
                time: "2023-11-3", msg: "调节灯光亮度。", state: 1
        }]
        }, {
            name: "智能冰箱", id: 2, type:'HA', SOC: 1, description: '冰箱正常工作中。',
            msg_list:[]
        }, {
            name: "扫地机器人", id: 3, type:'HA', SOC: 0.5, description: '很好用的扫地机器人。',
            msg_list: []
        }, {
            name: "智能空调", id: 4, type:'HA', SOC: 1, description: '空调现在27°。',
            msg_list: []
        }, {
            name: "智能电动车", id: 5, type:'EC', SOC: 0.15, description: '爱车该充电了。',
            msg_list: []
        }, {
            name: "智能门锁", id: 6, type:'Secure', SOC: 1, description: '门锁已上锁。',
            msg_list: []
        }, {
            name: "监控摄像头", id: 7, type:'Secure', SOC: 1, description: '监控摄像头正常工作中。',
            msg_list: []
        }]);
        const selected_devices = ref(devices.value);
        const select_tag = ref(['照明', '家电', '能耗', '安防', '其他']);

        const edit_visible = ref(false);
        const edit_form = ref({
            id: '',
            name: '',
            type: '',
        });

        const insert_visible = ref(false);
        const insert_form = ref({
            name: '',
            type: '',
        });

        const msg_visible = ref(false);
        const msg_list = ref({
            device_name: '' ,
            list: []
        });

        function type2str(type){
            switch(type){
                case 'light':
                    return '照明';
                case 'HA':
                    return '家电';
                case 'EC':
                    return '能耗';
                case 'Secure':
                    return '安防';
                case 'other':
                    return '其他';
            }
        }

        function updateSelect(value){
            select_tag.value = value;
            selected_devices.value = [];
            for(let i=0; i<devices.value.length; i++){
                if(select_tag.value.indexOf(type2str(devices.value[i].type)) !== -1){
                    selected_devices.value.push(devices.value[i]);
                }
            }
        }

        function editOk(){
            console.log(edit_form.value);
            edit_visible.value = false;
        }

        function editCancel(){
            edit_visible.value = false;
        }

        function insertOk(){
            console.log(insert_form.value);
            insert_visible.value = false;
        }

        function insertCancel(){
            insert_visible.value = false;
        }

        function openEditForm(device){
            edit_form.value.id = device.id;
            edit_visible.value = true;
        }

        function openInsertForm(){
            console.log('open');
            insert_visible.value = true;
        }

        function msgOk(){
            msg_visible.value = false;
        }

        function msgCancel(){
            msg_visible.value = false;
        }

        function openMsgList(device){
            msg_list.value.list = device.msg_list;
            msg_list.value.device_name = device.name;
            msg_visible.value = true;
        }

        return{
          devices, select_tag, selected_devices, edit_visible, edit_form, insert_visible, insert_form, msg_visible, msg_list,
          type2str, updateSelect, openEditForm, openInsertForm, editOk, editCancel, insertOk, insertCancel, msgOk, msgCancel, openMsgList
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