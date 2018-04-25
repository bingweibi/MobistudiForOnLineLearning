package czd.mobistudi.md.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import czd.mobistudi.md.model.api.ApiClient;
import czd.mobistudi.md.model.api.DefaultCallback;
import czd.mobistudi.md.model.entity.Result;
import czd.mobistudi.md.model.storage.LoginShared;
import czd.mobistudi.md.presenter.contract.ITopicHeaderPresenter;
import czd.mobistudi.md.ui.view.ITopicHeaderView;

import okhttp3.Headers;

public class TopicHeaderPresenter implements ITopicHeaderPresenter {

    private final Activity activity;
    private final ITopicHeaderView topicHeaderView;

    public TopicHeaderPresenter(@NonNull Activity activity, @NonNull ITopicHeaderView topicHeaderView) {
        this.activity = activity;
        this.topicHeaderView = topicHeaderView;
    }

    @Override
    public void collectTopicAsyncTask(@NonNull String topicId) {
        ApiClient.service.collectTopic(LoginShared.getAccessToken(activity), topicId).enqueue(new DefaultCallback<Result>(activity) {

            @Override
            public boolean onResultOk(int code, Headers headers, Result result) {
                topicHeaderView.onCollectTopicOk();
                return false;
            }

        });
    }

    @Override
    public void decollectTopicAsyncTask(@NonNull String topicId) {
        ApiClient.service.decollectTopic(LoginShared.getAccessToken(activity), topicId).enqueue(new DefaultCallback<Result>(activity) {

            @Override
            public boolean onResultOk(int code, Headers headers, Result result) {
                topicHeaderView.onDecollectTopicOk();
                return false;
            }

        });
    }

}
