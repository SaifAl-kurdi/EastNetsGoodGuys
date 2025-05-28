package repository;

import dtos.NotificationDTO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import model.NotificationModel;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NotificationRepository implements PanacheRepository<NotificationModel> {

    public NotificationDTO retrieveASuccessfulMessage() {
        try {
            NotificationModel model = findAll().firstResult();
            if (model == null) {
                return null;
            }
            return new NotificationDTO(model);
        } catch (Exception e) {
            return null;
        }
    }
}