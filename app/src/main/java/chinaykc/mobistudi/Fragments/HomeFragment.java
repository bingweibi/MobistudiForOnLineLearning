package chinaykc.mobistudi.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cat.mobistudi.CAT;
import chinaykc.mobistudi.MainActivity;
import chinaykc.mobistudi.R;
import chinaykc.mobistudi.studyprogress.StudyProgressActivity;
import xyz.kfdykme.mobistudi.activity.CourseDetailActivity;
import xyz.kfdykme.mobistudi.activity.CourseListActivity;
import xyz.kfdykme.mobistudi.bean.StudyUser;

/**
 * @author chinaykc
 * @date 17-5-22
 * 首页
 */

public class HomeFragment extends Fragment {

    private StudyUser userInfo;
    private TextView learnedDay;
    private CardView mCardView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userInfo = ((MainActivity)(activity)).getUserInfo();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    int flag = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        learnedDay = (TextView) view.findViewById(R.id.learnedDay);
        learnedDay.setText(String.valueOf(userInfo.getLearnedDay()));

        mCardView= (CardView) view.findViewById(R.id.change);
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 1) {
                    TextView title1 = (TextView) view.findViewById(R.id.recommendcoursetitle);
                    title1.setText("推荐 背包问题");
                    TextView content = (TextView) view.findViewById(R.id.recommendcoursecontent);
                    content.setText("从N件价值与重量不同的物品中选取部分物品，使选中物品总量不超过限制重量且价值和最大。");
                    TextView title2 = (TextView) view.findViewById(R.id.recommendcoursetitle2);
                    title2.setText("推荐 约瑟夫环");
                    TextView content2 = (TextView) view.findViewById(R.id.recommendcoursecontent2);
                    content2.setText("编号为1到n的n个人围成一个圈，从第1个人开始报数，报到m时停止报数，报m的人出圈，再从他的下一个人起重新报数，报到m时停止报数，报m的圈，如此下去，直到所有人全部出圈为止。");
                    flag= 0;
                } else {
                    TextView title1 = (TextView) view.findViewById(R.id.recommendcoursetitle);
                    title1.setText("推荐 迷宫问题");
                    TextView content = (TextView) view.findViewById(R.id.recommendcoursecontent);
                    content.setText("给定一个迷宫,入口为左上角,出口为右下角,是否有路径从入口到出口,若有则输出该路径。");
                    TextView title2 = (TextView) view.findViewById(R.id.recommendcoursetitle2);
                    title2.setText("推荐 DNA排序");
                    TextView content2 = (TextView) view.findViewById(R.id.recommendcoursecontent2);
                    content2.setText("以逆序数的方法分类DNA字符串，排序程度从好到差。所有字符串长度相同。");
                    flag = 1;
                }
            }

        });

        Button recommend1 = (Button) view.findViewById(R.id.recommendcourse);
        recommend1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
                startActivity(intent);
            }
        });
        Button recommend2 = (Button) view.findViewById(R.id.recommendcourse2);
        recommend2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
                startActivity(intent);
            }
        });

        Button btRateofProgress2 = (Button) view.findViewById(R.id.rateofprogress2);
        btRateofProgress2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StudyProgressActivity.class);
                startActivity(intent);
            }
        });

        Button catButton = (Button) view.findViewById(R.id.catButton);
        catButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CAT.class);
                intent.putExtra("userTheatRecord",userInfo.getCatTheat());
                startActivity(intent);
            }
        });

        Button btStarttolearn = (Button) view.findViewById(R.id.starttolearn);
        btStarttolearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CourseListActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

