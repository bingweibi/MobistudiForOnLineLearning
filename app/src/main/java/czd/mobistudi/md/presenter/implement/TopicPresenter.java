package czd.mobistudi.md.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import czd.mobistudi.md.model.api.ApiClient;
import czd.mobistudi.md.model.api.ApiDefine;
import czd.mobistudi.md.model.api.DefaultCallback;
import czd.mobistudi.md.model.entity.Result;
import czd.mobistudi.md.model.entity.TopicWithReply;
import czd.mobistudi.md.model.storage.LoginShared;
import czd.mobistudi.md.presenter.contract.ITopicPresenter;
import czd.mobistudi.md.ui.view.ITopicView;

import okhttp3.Headers;

public class TopicPresenter implements ITopicPresenter {

    private final Activity activity;
    private final ITopicView topicView;

    public TopicPresenter(@NonNull Activity activity, @NonNull ITopicView topicView) {
        this.activity = activity;
        this.topicView = topicView;
    }

    @Override
    public void getTopicAsyncTask(@NonNull String topicId) {
        ApiClient.service.getTopic(topicId, LoginShared.getAccessToken(activity), ApiDefine.MD_RENDER).enqueue(new DefaultCallback<Result.Data<TopicWithReply>>(activity) {

            @Override
            public boolean onResultOk(int code, Headers headers, Result.Data<TopicWithReply> result) {
                topicView.onGetTopicOk(result.getData());
                return false;
            }

            @Override
            public void onFinish() {
                topicView.onGetTopicFinish();
            }

        });
    }

}
