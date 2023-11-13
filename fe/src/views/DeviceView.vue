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

    <a-button type="primary" style="margin-left: 2%">
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
                        <span class="icon-hover" style="font-size: 20px"> <IconMessage /> </span>
                    </a-badge>
                    <span class="icon-hover" style="font-size: 20px" @click="openEditForm"> <icon-edit /> </span>
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

    <a-modal :visible="edit_visible" @ok="handleOk" @cancel="handleCancel">
        <template #title>
            Title
        </template>
        <a-form :model="form" visible="false">
            <a-form-item field="name" label="Username">
                <a-input v-model="form.name" placeholder="please enter your username..." />
            </a-form-item>
            <a-form-item field="post" label="Post">
                <a-input v-model="form.post" placeholder="please enter your post..." />
            </a-form-item>
            <a-form-item field="isRead">
                <a-checkbox v-model="form.isRead">
                    I have read the manual
                </a-checkbox>
            </a-form-item>
            <a-form-item>
                <a-button>Submit</a-button>
            </a-form-item>
        </a-form>
    </a-modal>

</template>

<script>

import {ref} from "vue";
export default {
    name: "DeviceView",

    setup() {
        const devices = ref([{
            name: "智能灯光", id: 1, type:'light', msg_cnt: 10, SOC: 1, description: '这是客厅的灯。'
        }, {
            name: "智能冰箱", id: 2, type:'HA', msg_cnt: 10, SOC: 1, description: '冰箱正常工作中。'
        }, {
            name: "扫地机器人", id: 3, type:'HA', msg_cnt: 10, SOC: 0.5, description: '很好用的扫地机器人。'
        }, {
            name: "智能空调", id: 4, type:'HA', msg_cnt: 10, SOC: 1, description: '空调现在27°。'
        }, {
            name: "智能电动车", id: 5, type:'EC', msg_cnt: 10, SOC: 0.15, description: '爱车该充电了。'
        }, {
            name: "智能门锁", id: 6, type:'Secure', msg_cnt: 10, SOC: 1, description: '门锁已上锁。'
        }, {
            name: "监控摄像头", id: 7, type:'Secure', msg_cnt: 10, SOC: 1, description: '监控摄像头正常工作中。'
        }]);
        const selected_devices = ref(devices.value);

        const select_tag = ref(['照明', '家电', '能耗', '安防', '其他']);

        const edit_visible = ref(false);

        const form = ref({
            name: '',
            post: '',
            isRead: false,
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

        function handleOk(){
            console.log(form.value);
            edit_visible.value = false;
        }

        function handleCancel(){
            edit_visible.value = false;
        }

        function openEditForm(){
            console.log('open');
            edit_visible.value = true;
        }

        return{
          devices, select_tag, selected_devices, edit_visible, form,
          type2str, updateSelect, handleOk, openEditForm, handleCancel
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