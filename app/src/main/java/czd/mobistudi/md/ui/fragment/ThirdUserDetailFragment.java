package czd.mobistudi.md.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import chinaykc.mobistudi.R;
import czd.mobistudi.md.model.entity.Topic;
import czd.mobistudi.md.model.entity.User;
import czd.mobistudi.md.model.storage.LoginShared;
import czd.mobistudi.md.presenter.contract.IUserDetailPresenter;
import czd.mobistudi.md.presenter.implement.UserDetailPresenter;
import czd.mobistudi.md.ui.adapter.UserDetailPagerAdapter;
import czd.mobistudi.md.ui.util.ToastUtils;
import czd.mobistudi.md.ui.view.IUserDetailView;

public class ThirdUserDetailFragment extends Fragment implements IUserDetailView {

    private Unbinder unbinder;
    private Activity mActivityContext;

    public ThirdUserDetailFragment() {
        // Required empty public constructor
    }

    public static ThirdUserDetailFragment newInstance() {
        return new ThirdUserDetailFragment();
    }

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private UserDetailPagerAdapter adapter;

    private IUserDetailPresenter userDetailPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third_user_detail, container, false);
        unbinder = ButterKnife.bind(this,view);
        mActivityContext = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new UserDetailPagerAdapter(mActivityContext, viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        tabLayout.setupWithViewPager(viewPager);

        userDetailPresenter = new UserDetailPresenter(mActivityContext, this);
        userDetailPresenter.getUserAsyncTask(LoginShared.getLoginName(mActivityContext));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onGetUserOk(@NonNull User user) {
        adapter.setUser(user);
    }

    @Override
    public void onGetCollectTopicListOk(@NonNull List<Topic> topicList) {
        adapter.setCollectTopicList(topicList);
    }

    @Override
    public void onGetUserError(@NonNull String message) {
        ToastUtils.with(mActivityContext).show(message);
    }

    @Override
    public void onGetUserStart() {

    }

    @Override
    public void onGetUserFinish() {

    }
}
