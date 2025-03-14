package cn.edu.zju.cs.bs;

import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import com.alibaba.fastjson.JSONObject;

@Data
public class WorkerThread extends Thread {
    private boolean running = true;
    private Integer deviceId;
    private String mqttServer;
    private String topic;
    private String clientPrefix;

    public void run() {
        try {
            Integer clientId;
            String content;
            int qos = 2;
            MemoryPersistence persistence = new MemoryPersistence();

            Random rand = new Random();

            clientId = deviceId;
            MqttClient mqttClient = new MqttClient(mqttServer, clientId.toString(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + mqttServer);
            mqttClient.connect(connOpts);
            System.out.println("Connected");
            while (running) {
                //随机等待10秒
                int interval = rand.nextInt(10);
                Thread.sleep(interval * 1000);

                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date now = new Date();
                int value = rand.nextInt(100);
                IOTMessage msg = new IOTMessage();
                msg.setDeviceId(clientId);
                msg.setData("Device Data " + sdf.format(now));
                msg.setValue(value);
                //超过80告警
                msg.setState(value > 20 ? 1 : 0);
                rand.nextFloat();
                //根据杭州经纬度随机生成设备位置信息
                msg.setLng(119.9 + rand.nextFloat() * 0.6);
                msg.setLat(30.1 + rand.nextFloat() * 0.4);
                msg.setTime(sdf.format(now));
                content = JSONObject.toJSONString(msg);
                System.out.println("Publishing message: " + content);
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);
                mqttClient.publish(topic, message);
                System.out.println("Message published");
            }
            mqttClient.disconnect();
            System.out.println("Disconnected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
