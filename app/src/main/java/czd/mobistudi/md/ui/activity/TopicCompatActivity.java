package czd.mobistudi.md.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import chinaykc.mobistudi.R;
import czd.mobistudi.md.model.api.ApiDefine;
import czd.mobistudi.md.model.entity.Reply;
import czd.mobistudi.md.model.entity.Topic;
import czd.mobistudi.md.model.entity.TopicWithReply;
import czd.mobistudi.md.model.storage.LoginShared;
import czd.mobistudi.md.presenter.contract.IReplyPresenter;
import czd.mobistudi.md.presenter.contract.ITopicHeaderPresenter;
import czd.mobistudi.md.presenter.contract.ITopicPresenter;
import czd.mobistudi.md.presenter.implement.ReplyPresenter;
import czd.mobistudi.md.presenter.implement.TopicHeaderPresenter;
import czd.mobistudi.md.presenter.implement.TopicPresenter;
import czd.mobistudi.md.ui.activity.base.StatusBarActivity;
import czd.mobistudi.md.ui.dialog.CreateReplyDialog;
import czd.mobistudi.md.ui.listener.DoubleClickBackToContentTopListener;
import czd.mobistudi.md.ui.listener.FloatingActionButtonBehaviorListener;
import czd.mobistudi.md.ui.listener.NavigationFinishClickListener;
import czd.mobistudi.md.ui.listener.TopicJavascriptInterface;
import czd.mobistudi.md.ui.util.Navigator;
import czd.mobistudi.md.ui.util.ThemeUtils;
import czd.mobistudi.md.ui.view.IBackToContentTopView;
import czd.mobistudi.md.ui.view.ICreateReplyView;
import czd.mobistudi.md.ui.view.IReplyView;
import czd.mobistudi.md.ui.view.ITopicHeaderView;
import czd.mobistudi.md.ui.view.ITopicView;
import czd.mobistudi.md.ui.widget.TopicWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TopicCompatActivity extends StatusBarActivity implements ITopicView, ITopicHeaderView, IReplyView, IBackToContentTopView, SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.web_topic)
    TopicWebView webTopic;

    @BindView(R.id.fab_reply)
    FloatingActionButton fabReply;

    private String topicId;
    private Topic topic;

    private ICreateReplyView createReplyView;

    private ITopicPresenter topicPresenter;
    private ITopicHeaderPresenter topicHeaderPresenter;
    private IReplyPresenter replyPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_compat);
        ButterKnife.bind(this);

        topicId = getIntent().getStringExtra(Navigator.TopicWithAutoCompat.EXTRA_TOPIC_ID);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.topic);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setOnClickListener(new DoubleClickBackToContentTopListener(this));

        topicPresenter = new TopicPresenter(this, this);
        topicHeaderPresenter = new TopicHeaderPresenter(this, this);
        replyPresenter = new ReplyPresenter(this, this);

        createReplyView = CreateReplyDialog.createWithAutoTheme(this, topicId, this);

        webTopic.addOnScrollListener(new FloatingActionButtonBehaviorListener.ForWebView(fabReply));
        webTopic.setBridgeAndLoadPage(new TopicJavascriptInterface(this, createReplyView, topicHeaderPresenter, replyPresenter));

        refreshLayout.setColorSchemeResources(R.color.color_accent);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                if (topic != null) {
                    Navigator.openShare(this, "《" + topic.getTitle() + "》\n" + ApiDefine.TOPIC_LINK_URL_PREFIX + topicId);
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onRefresh() {
        topicPresenter.getTopicAsyncTask(topicId);
    }

    @OnClick(R.id.fab_reply)
    void onBtnReplyClick() {
        if (topic != null && LoginActivity.checkLogin(this)) {
            createReplyView.showWindow();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginActivity.REQUEST_DEFAULT && resultCode == RESULT_OK) {
            refreshLayout.setRefreshing(true);
            onRefresh();
        }
    }

    @Override
    public void onGetTopicOk(@NonNull TopicWithReply topic) {
        this.topic = topic;
        webTopic.updateTopicAndUserId(topic, LoginShared.getId(this));
    }

    @Override
    public void onGetTopicFinish() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void appendReplyAndUpdateViews(@NonNull Reply reply) {
        webTopic.appendReply(reply);
    }

    @Override
    public void onCollectTopicOk() {
        webTopic.updateTopicCollect(true);
    }

    @Override
    public void onDecollectTopicOk() {
        webTopic.updateTopicCollect(false);
    }

    @Override
    public void onUpReplyOk(@NonNull Reply reply) {
        webTopic.updateReply(reply);
    }

    @Override
    public void backToContentTop() {
        webTopic.scrollTo(0, 0);
    }

}
