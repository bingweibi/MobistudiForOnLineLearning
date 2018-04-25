package czd.mobistudi.md.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import chinaykc.mobistudi.R;
import czd.mobistudi.md.model.entity.Tab;
import czd.mobistudi.md.model.entity.Topic;

import czd.mobistudi.md.presenter.contract.IMainPresenter;
import czd.mobistudi.md.presenter.implement.MainPresenter;
import czd.mobistudi.md.ui.activity.CreateTopicActivity;
import czd.mobistudi.md.ui.activity.LoginActivity;
import czd.mobistudi.md.ui.listener.FloatingActionButtonBehaviorListener;
import czd.mobistudi.md.ui.util.ToastUtils;
import czd.mobistudi.md.ui.view.IBackToContentTopView;
import czd.mobistudi.md.ui.view.IMainView;
import czd.mobistudi.md.ui.viewholder.LoadMoreFooter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import czd.mobistudi.md.ui.adapter.SecondRecyclerViewAdapter;

/**
 * Created by thugwar on 2018/3/8.
 */

public class SecondRecyclerViewFragment extends Fragment implements IMainView, IBackToContentTopView, SwipeRefreshLayout.OnRefreshListener, LoadMoreFooter.OnLoadMoreListener{

    private static final boolean IS_GRID_LAYOUT = false;
    private  static final String TAB = "tab";
    private Unbinder unbinder;

    private int page = 1;
    private Tab mTab;
    private Activity mActivityContext;

     private  SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler_view)
     HeaderAndFooterRecyclerView mRecyclerView;

    @BindView(R.id.fab_create_topic)
     FloatingActionButton fabCreateTopic;

    private LoadMoreFooter loadMoreFooter;
    private SecondRecyclerViewAdapter adapter;

    private IMainPresenter mainPresenter;


    public SecondRecyclerViewFragment() {
        // Required empty public constructor
    }
    public static SecondRecyclerViewFragment newInstance(Tab tab) {
        SecondRecyclerViewFragment fragment = new SecondRecyclerViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(TAB,tab);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTab = (Tab) getArguments().getSerializable(TAB);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header_footer_recyclerview, container, false);
        if((mActivityContext = getActivity())==null){
            ToastUtils.with(view.getContext()).show("SecondRecyclerViewFragment is associated with null activity");
        }
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout = view.findViewById(R.id.refresh_layout);

        //setup materialviewpager

        if (IS_GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
//        mRecyclerView.setHasFixedSize(true);

        //Use this now
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
//        mRecyclerView.setAdapter(new TestRecyclerViewAdapter(items, IS_GRID_LAYOUT));

        mainPresenter = new MainPresenter(mActivityContext, this);
        loadMoreFooter = new LoadMoreFooter(view.getContext(), mRecyclerView, this);
        mRecyclerView.addOnScrollListener(new FloatingActionButtonBehaviorListener.ForRecyclerView(fabCreateTopic));

        adapter = new SecondRecyclerViewAdapter(mActivityContext);
        mRecyclerView.setAdapter(adapter);

        refreshLayout.setColorSchemeResources(R.color.color_accent);
        refreshLayout.setOnRefreshListener(this);
        mainPresenter.switchTab(mTab);
    }

    //Override

    @OnClick(R.id.fab_create_topic)
    void onBtnCreateTopicClick() {
        if (LoginActivity.checkLogin(mActivityContext)) {
            startActivity(new Intent(mActivityContext, CreateTopicActivity.class));
        }
    }

    @Override
    public void onRefresh() {
        mainPresenter.refreshTopicListAsyncTask();
    }

    @Override
    public void backToContentTop() {
        mRecyclerView.scrollToPosition(0);
        fabCreateTopic.show();
    }

    @Override
    public void onSwitchTabOk(@NonNull Tab tab) {
        page = 0;
//        toolbar.setTitle(tab.getNameId());
        fabCreateTopic.show();
        adapter.clearTopicListAndNotify();
        loadMoreFooter.setState(LoadMoreFooter.STATE_DISABLED);
        refreshLayout.setRefreshing(true);
        onRefresh();
    }


    @Override
    public void onRefreshTopicListOk(@NonNull List<Topic> topicList) {
        page = 1;
        adapter.setTopicListAndNotify(topicList);
        refreshLayout.setRefreshing(false);
        loadMoreFooter.setState(topicList.isEmpty() ? LoadMoreFooter.STATE_DISABLED : LoadMoreFooter.STATE_ENDLESS);
    }

    @Override
    public void onRefreshTopicListError(@NonNull String message) {
        ToastUtils.with(getContext()).show(message);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreTopicListOk(@NonNull List<Topic> topicList) {
        page++;
        adapter.appendTopicListAndNotify(topicList);
        if (topicList.isEmpty()) {
            loadMoreFooter.setState(LoadMoreFooter.STATE_FINISHED);
        } else {
            loadMoreFooter.setState(LoadMoreFooter.STATE_ENDLESS);
        }
    }

    @Override
    public void onLoadMoreTopicListError(@NonNull String message) {
        ToastUtils.with(getContext()).show(message);
        loadMoreFooter.setState(LoadMoreFooter.STATE_FAILED);
    }

    @Override
    public void updateUserInfoViews() {
//        if (TextUtils.isEmpty(LoginShared.getAccessToken(this))) {
//            GlideApp.with(this).load(R.drawable.image_placeholder).placeholder(R.drawable.image_placeholder).into(imgAvatar);
//            tvLoginName.setText(R.string.click_avatar_to_login);
//            tvScore.setText(null);
//            btnLogout.setVisibility(View.GONE);
//        } else {
//            GlideApp.with(this).load(LoginShared.getAvatarUrl(this)).placeholder(R.drawable.image_placeholder).into(imgAvatar);
//            tvLoginName.setText(LoginShared.getLoginName(this));
//            tvScore.setText(getString(R.string.score__, LoginShared.getScore(this)));
//            btnLogout.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void updateMessageCountViews(int count) {
//        navItemNotification.setBadge(count);
    }

    @Override
    public void onLoadMore() {
        mainPresenter.loadMoreTopicListAsyncTask(page + 1);
    }
}
