package czd.mobistudi.md.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import chinaykc.mobistudi.R;
import czd.mobistudi.md.model.api.ApiClient;
import czd.mobistudi.md.model.api.DefaultCallback;
import czd.mobistudi.md.model.entity.Author;
import czd.mobistudi.md.model.entity.Reply;
import czd.mobistudi.md.model.entity.Result;
import czd.mobistudi.md.model.storage.LoginShared;
import czd.mobistudi.md.model.storage.SettingShared;
import czd.mobistudi.md.presenter.contract.ICreateReplyPresenter;
import czd.mobistudi.md.ui.view.ICreateReplyView;
import org.joda.time.DateTime;

import java.util.ArrayList;

import okhttp3.Headers;

public class CreateReplyPresenter implements ICreateReplyPresenter {

    private final Activity activity;
    private final ICreateReplyView createReplyView;

    public CreateReplyPresenter(@NonNull Activity activity, @NonNull ICreateReplyView createReplyView) {
        this.activity = activity;
        this.createReplyView = createReplyView;
    }

    @Override
    public void createReplyAsyncTask(@NonNull String topicId, String content, final String targetId) {
        if (TextUtils.isEmpty(content)) {
            createReplyView.onContentError(activity.getString(R.string.content_empty_error_tip));
        } else {
            final String finalContent;
            if (SettingShared.isEnableTopicSign(activity)) { // 添加小尾巴
                finalContent = content + "\n\n" + SettingShared.getTopicSignContent(activity);
            } else {
                finalContent = content;
            }
            createReplyView.onReplyTopicStart();
            ApiClient.service.createReply(topicId, LoginShared.getAccessToken(activity), finalContent, targetId).enqueue(new DefaultCallback<Result.ReplyTopic>(activity) {

                @Override
                public boolean onResultOk(int code, Headers headers, Result.ReplyTopic result) {
                    Reply reply = new Reply();
                    reply.setId(result.getReplyId());
                    Author author = new Author();
                    author.setLoginName(LoginShared.getLoginName(getActivity()));
                    author.setAvatarUrl(LoginShared.getAvatarUrl(getActivity()));
                    reply.setAuthor(author);
                    reply.setContentFromLocal(finalContent); // 这里要使用本地的访问器
                    reply.setCreateAt(new DateTime());
                    reply.setUpList(new ArrayList<String>());
                    reply.setReplyId(targetId);
                    createReplyView.onReplyTopicOk(reply);
                    return false;
                }

                @Override
                public void onFinish() {
                    createReplyView.onReplyTopicFinish();
                }

            });
        }
    }

}
