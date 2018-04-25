package czd.mobistudi.md.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import chinaykc.mobistudi.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import czd.mobistudi.md.ui.adapter.FirstRecyclerViewAdapter;
import chinaykc.mobistudi.HotCourse;


public class FirstRecyclerViewFragment extends Fragment {

    private static final boolean IS_GRID_LAYOUT = true;
    private static final int ITEM_COUNT = 30;

    private List<Object> hotCourseList = new ArrayList<>();
    private FirstRecyclerViewAdapter mAdapter;

    public static FirstRecyclerViewFragment newInstance() {
        return new FirstRecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ButterKnife.bind(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.mobar_recycler_view);
        //setup material view pager
        if (IS_GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);

        //Use this now
        initCourses();
        if (IS_GRID_LAYOUT) {
            mAdapter = new FirstRecyclerViewAdapter(hotCourseList, FirstRecyclerViewAdapter.LAYOUT_GRID);
        }else{
            mAdapter = new FirstRecyclerViewAdapter(hotCourseList, FirstRecyclerViewAdapter.LAYOUT_LINEAR_MIX);
        }
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setAdapter(mAdapter);

        final SwipeRefreshLayout swipeRefresh=(SwipeRefreshLayout)view.findViewById(R.id.mobar_swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mAdapter.getLayoutType() == FirstRecyclerViewAdapter.LAYOUT_LINEAR_MIX){
                    mAdapter.initialItemTypeArray();
                }
                initCourses();
                mAdapter.notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            }
        });
    }


    private void initCourses() {
        hotCourseList.clear();
        for (int i = 0; i < ITEM_COUNT; i++) {
            final Random random = new Random();
            int index = random.nextInt(hotCourses.length);
            hotCourseList.add(hotCourses[index]);
        }
    }

    private HotCourse[] hotCourses = {new HotCourse("数据结构在生活中的应用", R.drawable.course_pic_p1), new HotCourse("神奇的蒙特卡洛模拟", R.drawable.course_pic_p2),
            new HotCourse("高精度算法", R.drawable.course_pic_p3), new HotCourse("震惊！这竟然是哈夫曼的真面目", R.drawable.course_pic_p4),
            new HotCourse("图论的简要概括", R.drawable.course_pic_p6), new HotCourse("你了解“队列”吗？", R.drawable.course_pic_p5),
            new HotCourse("扑克牌中的有趣问题", R.drawable.course_pic_p7), new HotCourse("你知道八皇后问题吗？", R.drawable.course_pic_p8),
            new HotCourse("马的哈密尔顿问题", R.drawable.course_pic_p9), new HotCourse("约瑟夫环", R.drawable.course_pic_p10),
            new HotCourse("电话号码查询问题", R.drawable.course_pic_p11),new HotCourse("差分约束系统", R.drawable.course_pic_p12)};
}
