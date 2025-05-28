package kafkaTopic;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.Properties;

@ApplicationScoped
public class KafkaTopicCreator {

    @PostConstruct
    public void createTopic() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        
        try (AdminClient adminClient = AdminClient.create(props)) {
            NewTopic topic = new NewTopic("notification-topic", 3, (short) 1);
            adminClient.createTopics(Collections.singleton(topic)).all().get();
            System.out.println("Kafka topic 'notification-topic' created successfully.");
        } catch (Exception e) {
            System.err.println("Error creating Kafka topic: " + e.getMessage());
        }
    }
}