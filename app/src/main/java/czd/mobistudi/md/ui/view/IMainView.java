package czd.mobistudi.md.ui.view;

import android.support.annotation.NonNull;

import czd.mobistudi.md.model.entity.Tab;
import czd.mobistudi.md.model.entity.Topic;

import java.util.List;

public interface IMainView {

    void onSwitchTabOk(@NonNull Tab tab);

    void onRefreshTopicListOk(@NonNull List<Topic> topicList);

    void onRefreshTopicListError(@NonNull String message);

    void onLoadMoreTopicListOk(@NonNull List<Topic> topicList);

    void onLoadMoreTopicListError(@NonNull String message);

    void updateUserInfoViews();

    void updateMessageCountViews(int count);

}
