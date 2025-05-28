package frontEnd.beans;

import frontEnd.dto.NotificationStore;
import frontEnd.kafka.NotificationConsumer;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

import javax.management.Notification;
import java.io.Serializable;

@Named
@SessionScoped
@Setter
public class NotificationBean implements Serializable {

    @Inject
    private NotificationStore notificationStore;
    @Inject
    NotificationConsumer notificationConsumer;

    @Getter @Setter
    private String notification;

    public String getDisplayNotification() {
        notificationConsumer.init();
        String msg = notificationStore.getNotification();
        if (msg == null || msg.trim().isEmpty()) {
            return "There is no notification now!";
        }
        return msg;
    }

    public void refresh() {
        getDisplayNotification();
        PrimeFaces.current().ajax().update("notificationPanel");
    }
}
