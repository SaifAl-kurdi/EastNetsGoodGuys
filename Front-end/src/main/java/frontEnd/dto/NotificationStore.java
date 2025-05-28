package frontEnd.dto;

import jakarta.enterprise.context.ApplicationScoped;
import java.io.Serializable;

@ApplicationScoped
public class NotificationStore implements Serializable {

    private String notification;

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getNotification() {
        return notification;
    }
}
