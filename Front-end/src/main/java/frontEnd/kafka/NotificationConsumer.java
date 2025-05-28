package frontEnd.kafka;

import frontEnd.beans.NotificationBean;
import frontEnd.dto.NotificationStore;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

@ApplicationScoped
public class NotificationConsumer {

    @Inject
    NotificationStore notificationStore;

    private Thread consumerThread;
    private volatile boolean keepPolling = true;

    public void init() {
        consumerThread = new Thread(this::startConsumerLoop, "Kafka-Notification-Consumer");
        consumerThread.start();
    }

    public void startConsumerLoop() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(Arrays.asList("notification-topic"));

            while (keepPolling) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1001));
                if (!records.isEmpty()) {
                    for (ConsumerRecord<String, String> record : records) {
                        notificationStore.setNotification(record.value());
                        return;
                    }
                    consumer.commitSync();
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("NotificationConsumer: Shutdown initiated.");
        keepPolling = false;
        if (consumerThread != null && consumerThread.isAlive()) {
            try {
                consumerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("NotificationConsumer: Shut down.");
    }
}
/*    public static void main(String[] args) {
        String lastMessage = null;
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(Arrays.asList("notification-topic"));
            System.out.println("Subscribed to topic: notification-topic");

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1001));
                if (!records.isEmpty()) {
                    for (ConsumerRecord<String, String> record : records) {
                        lastMessage = record.value();
                        System.out.println("Received message: " + record.value());
                    }
                    consumer.commitSync();
                }
                if(lastMessage != null) {
                    System.out.println("Last message so far: " + lastMessage);
                }
            }
        }
    }*/

