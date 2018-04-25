package czd.mobistudi.md.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import chinaykc.mobistudi.R;

import java.util.HashMap;
import java.util.List;

import czd.mobistudi.mo.activity.MobarActivity;
import czd.mobistudi.md.ui.adapter.MobarRecyclerViewAdapter;
import czd.mobistudi.md.model.entity.BlogListBean;
import czd.mobistudi.md.model.manager.BlogListDataManager;
import czd.mobistudi.md.model.manager.OkhttpManager;
import czd.mobistudi.md.ui.widget.circlerefresh.CircleRefreshLayout;
import okhttp3.Request;

/**
 * Created by thugwar on 2017/10/16.
 */

public class MobarFragment extends Fragment implements CircleRefreshLayout.OnCircleRefreshListener,View.OnClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    private CircleRefreshLayout mRefreshLayout;
    private SliderLayout mDemoSlider;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private static final String CRAWLER_URL = "http://blog.jobbole.com/tag/%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84/page";
    private List<BlogListBean> mBlogListBeans;

    private Activity mActivityContext;
    private MobarRecyclerViewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivityContext = getActivity();
        return inflater.inflate(R.layout.mobar_frgment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        view.findViewById(R.id.enter_mobar).setOnClickListener(this);

        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mDemoSlider = view.findViewById(R.id.slider);
        mRecyclerView = view.findViewById(R.id.mobarfragment_recyclerview);
        mProgressBar = view.findViewById(R.id.progress_bar);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MobarRecyclerViewAdapter(mActivityContext);
        mRecyclerView.setAdapter(mAdapter);

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("电影票福利限量抢", R.drawable.mobar_pager_pic_hot1);
        file_maps.put("2018校园招聘", R.drawable.mobar_pager_pic_hot2);
        file_maps.put("5天玩书活动", R.drawable.mobar_pager_pic_hot3);
        file_maps.put("电竞专业师生备战双11", R.drawable.mobar_pager_pic_hot4);
        file_maps.put("参加活动赢取电影票", R.drawable.mobar_pager_pic_hot5);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        mRefreshLayout.setOnRefreshListener(this);

        loadingData();
    }

    public void loadingData(){
        if(mProgressBar.getVisibility() == View.GONE){
            mProgressBar.setVisibility(View.VISIBLE);
        }
        if(mRecyclerView.getVisibility() == View.VISIBLE){
            mRecyclerView.setVisibility(View.GONE);
        }
        for(int i=0;i<3;i++){
            bindData(CRAWLER_URL+i);
        }
    }

    private void bindData(final String url){
        OkhttpManager.getAsync(url, new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                Log.d("MobarFragment","URL load fail...");
            }
            @Override
            public void requestSuccess(String result) {
                Document document= Jsoup.parse(result, url);
                mBlogListBeans = BlogListDataManager.getBlogListBeans(document);
                if(mBlogListBeans!=null){
                    mAdapter.appendTopicListAndNotify(mBlogListBeans);
                    if(mProgressBar.getVisibility() == View.VISIBLE){
                        mProgressBar.setVisibility(View.GONE);
                    }
                    if(mRecyclerView.getVisibility() == View.GONE){
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                }else{
                    Log.d("MobarFragment","NULL BlogListBeans");
                }
            }
        });
    }

    @Override
    public void refreshing() {
        // do something when refresh starts
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mRefreshLayout.finishRefreshing();
            }
        }, 1200);
    }

    @Override
    public void completeRefresh() {
        // do something when refresh complete
        Toast.makeText(getContext(),"Refresh Complete",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getContext(),slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.enter_mobar:
                startActivity(new Intent(mActivityContext,MobarActivity.class));
                break;
            default:
                break;
        }
    }

}