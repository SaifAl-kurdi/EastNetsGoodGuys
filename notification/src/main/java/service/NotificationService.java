package service;

import dtos.NotificationDTO;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import repository.NotificationRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class NotificationService {

    @Inject
    NotificationRepository notificationRepository;

    @Inject
    @Channel("notificationsOut")
    Emitter<String> emitter;

    public String notificationServices(String fileName, int goodGuysCount, String authHeader) {
        try {
            NotificationDTO notificationDTO = notificationRepository.retrieveASuccessfulMessage();
            String baseMessage = (notificationDTO != null) ? notificationDTO.getMessage() : "Something went wrong";
            String finalMessage = "The File name " + fileName + ", and there are  " + goodGuysCount + " goodGuys. And " + baseMessage;
            emitter.send(finalMessage);
            return finalMessage;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
