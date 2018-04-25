package czd.mobistudi.md.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import chinaykc.mobistudi.R;
import czd.mobistudi.md.model.api.ApiDefine;
import czd.mobistudi.md.model.entity.Reply;
import czd.mobistudi.md.model.entity.Topic;
import czd.mobistudi.md.model.entity.TopicWithReply;
import czd.mobistudi.md.model.storage.SettingShared;
import czd.mobistudi.md.model.util.EntityUtils;
import czd.mobistudi.md.presenter.contract.ITopicPresenter;
import czd.mobistudi.md.presenter.implement.TopicPresenter;
import czd.mobistudi.md.ui.adapter.ReplyListAdapter;
import czd.mobistudi.md.ui.activity.base.StatusBarActivity;
import czd.mobistudi.md.ui.dialog.AlertDialogUtils;
import czd.mobistudi.md.ui.dialog.CreateReplyDialog;
import czd.mobistudi.md.ui.listener.DoubleClickBackToContentTopListener;
import czd.mobistudi.md.ui.listener.FloatingActionButtonBehaviorListener;
import czd.mobistudi.md.ui.listener.NavigationFinishClickListener;
import czd.mobistudi.md.ui.util.Navigator;
import czd.mobistudi.md.ui.util.ThemeUtils;
import czd.mobistudi.md.ui.view.IBackToContentTopView;
import czd.mobistudi.md.ui.view.ICreateReplyView;
import czd.mobistudi.md.ui.view.ITopicView;
import czd.mobistudi.md.ui.viewholder.TopicHeader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TopicActivity extends StatusBarActivity implements ITopicView, IBackToContentTopView, SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler_view)
    HeaderAndFooterRecyclerView recyclerView;

    @BindView(R.id.fab_reply)
    FloatingActionButton fabReply;

    private String topicId;
    private Topic topic;

    private ICreateReplyView createReplyView;
    private TopicHeader header;
    private ReplyListAdapter adapter;

    private ITopicPresenter topicPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);

//        if (SettingShared.isShowTopicRenderCompatTip(this)) {
//            SettingShared.markShowTopicRenderCompatTip(this);
//            AlertDialogUtils.createBuilderWithAutoTheme(this)
//                    .setMessage(R.string.topic_render_compat_tip)
//                    .setPositiveButton(R.string.ok, null)
//                    .show();
//        }

        topicId = getIntent().getStringExtra(Navigator.TopicWithAutoCompat.EXTRA_TOPIC_ID);

        if (!TextUtils.isEmpty(getIntent().getStringExtra(Navigator.TopicWithAutoCompat.EXTRA_TOPIC))) {
            topic = EntityUtils.gson.fromJson(getIntent().getStringExtra(Navigator.TopicWithAutoCompat.EXTRA_TOPIC), Topic.class);
        }

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.topic);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setOnClickListener(new DoubleClickBackToContentTopListener(this));

        createReplyView = CreateReplyDialog.createWithAutoTheme(this, topicId, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        header = new TopicHeader(this, recyclerView);
        header.updateViews(topic, false, 0);
        adapter = new ReplyListAdapter(this, createReplyView);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new FloatingActionButtonBehaviorListener.ForRecyclerView(fabReply));

        topicPresenter = new TopicPresenter(this, this);

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
        header.updateViews(topic);
        adapter.setReplyListAndNotify(topic.getAuthor().getLoginName(), topic.getReplyList());
    }

    @Override
    public void onGetTopicFinish() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void appendReplyAndUpdateViews(@NonNull Reply reply) {
        adapter.appendReplyAndNotify(reply);
        header.updateReplyCount(adapter.getItemCount());
        recyclerView.smoothScrollToPosition(adapter.getItemCount());
    }

    @Override
    public void backToContentTop() {
        recyclerView.scrollToPosition(0);
    }

}
