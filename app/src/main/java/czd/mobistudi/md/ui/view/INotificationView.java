package czd.mobistudi.md.ui.view;

import android.support.annotation.NonNull;

import czd.mobistudi.md.model.entity.Notification;

public interface INotificationView {

    void onGetMessagesOk(@NonNull Notification notification);

    void onGetMessagesFinish();

    void onMarkAllMessageReadOk();

}
