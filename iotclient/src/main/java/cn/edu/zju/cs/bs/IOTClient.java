package cn.edu.zju.cs.bs;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Vector;

public class IOTClient {
    public static void main(String[] args) {
        int devices1 = 0;
        int devices2 = 1;
        String mqttServer = "tcp://localhost:1883";
        String topic = "testapp";
        String clientPrefix = "device";

        try {
            Properties properties = new Properties();
            FileInputStream in = new FileInputStream("iot.properties");
            properties.load(in);
            devices1 = Integer.parseInt(properties.getProperty("devices1"));
            devices2 = Integer.parseInt(properties.getProperty("devices2"));

            mqttServer = properties.getProperty("server");
            topic = properties.getProperty("topic");
            clientPrefix = properties.getProperty("prefix");

            Vector<WorkerThread> threadVector = new Vector<WorkerThread>();
            for (int i = devices1; i < devices2; i++) {
                WorkerThread thread = new WorkerThread();
                thread.setDeviceId(i + 1);
                thread.setMqttServer(mqttServer);
                thread.setTopic(topic);
                thread.setClientPrefix(clientPrefix);
                threadVector.add(thread);
                thread.start();
            }
            for (WorkerThread thread : threadVector) {
                thread.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
