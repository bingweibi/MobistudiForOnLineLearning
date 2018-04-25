package czd.mobistudi.md.ui.view;

import android.support.annotation.NonNull;

import czd.mobistudi.md.model.entity.Reply;
import czd.mobistudi.md.model.entity.TopicWithReply;

public interface ITopicView {

    void onGetTopicOk(@NonNull TopicWithReply topic);

    void onGetTopicFinish();

    void appendReplyAndUpdateViews(@NonNull Reply reply);

}
