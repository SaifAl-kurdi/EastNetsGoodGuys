package dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.NotificationModel;

@NoArgsConstructor
@Getter @Setter
public class NotificationDTO {
    private int id;
    private String message;

    public NotificationDTO(NotificationModel notificationModel) {
        this.id = notificationModel.getId();
        this.message = notificationModel.getMessage();
    }
}