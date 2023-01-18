package com.example.backend.service;
import com.example.backend.model.Device;
import com.example.backend.model.EnergyConsumption;
import com.example.backend.model.TextMessageDTO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Optional;

@Component
public class Recv {
    //private final static String QUEUE_NAME = "hello";

    private final static String QUEUE_NAME = "queue";
//    @RabbitListener(queues=QUEUE_NAME)
//    public static void main(String[] argv) throws Exception {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
//
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String message = new String(delivery.getBody(), "UTF-8");
//            System.out.println(" [x] Received '" + message + "'");
//        };
//        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
//    }
//

    private int counter=0;
    private double totalConsumption=0;
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SimpMessagingTemplate template;

    @RabbitListener(queues = QUEUE_NAME)
    @Bean
    public void consumeMessages () throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("host.docker.internal");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            JSONParser parser = new JSONParser();
            //counter++;

            try{
                JSONObject jsonObject = (JSONObject) parser.parse(message);
                double value = Double.parseDouble(jsonObject.get("measurementValue").toString());Double.parseDouble(jsonObject.get("measurementValue").toString());

                counter++;
                totalConsumption = totalConsumption + value;

                String timestamp = (String) jsonObject.get("timestamp");
                Long id = (Long) jsonObject.get("deviceId");

                if(deviceService!=null) {

                    Device device = deviceService.getDeviceWithId(id);
                    EnergyConsumption consumption = new EnergyConsumption(timestamp, (int) value);

                    consumption.setDevice(device);

                    if (totalConsumption > device.getConsumption() && template != null) {
                        template.convertAndSend("/topic/message",
                                new TextMessageDTO("Device with Id " +
                                        device.getId() +
                                        " has a greater consumption than the maximum allowed: " +
                                        device.getConsumption() //+ " " +
                                        //totalConsumption
                                ));
                    }
                    //new TextMessageDTO("Poate nu mergi"));
                    counter = 0;
                    totalConsumption = 0;
                    System.out.println(template);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }


        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
