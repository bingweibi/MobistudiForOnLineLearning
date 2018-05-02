package chinaykc.mobistudi.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import chinaykc.mobistudi.R;
import chinaykc.mobistudi.SeriesContent.ActivitySeriesContent;
import chinaykc.mobistudi.Series.ActivityAllSeries;

/**
 * @author chinaykc
 * @date 17-6-3
 * 系列
 */

public class SeriesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.series,container,false);

        Glide.with(this)
                .load(R.drawable.zhuanti0)
                //.bitmapTransform(new BlurTransformation(mContext,15,2)) // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                .into((ImageView)view.findViewById(R.id.zhuanti0));

        CardView cardView =  view.findViewById(R.id.best_series_1);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivitySeriesContent.class);
                intent.putExtra("title","约瑟夫环");
                intent.putExtra("content","编号为1到n的n个人围成一个圈，从第1个人开始报数，报到m时停止报数，报m的人出圈，再从他的下一个人起重新报数，报到m时停止报数，报m的圈，如此下去，直到所有人全部出圈为止");
                intent.putExtra("position",1);
                startActivity(intent);
            }
        });
        CardView cardView1 = (CardView) view.findViewById(R.id.best_series_2);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivitySeriesContent.class);
                intent.putExtra("title","八皇后问题");
                intent.putExtra("content","在国际象棋棋盘上，每行放置一个皇后，且在竖方向，斜方向都没有冲突。");
                intent.putExtra("position",2);
                startActivity(intent);
            }
        });
        CardView cardView2 = (CardView) view.findViewById(R.id.best_series_3);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivitySeriesContent.class);
                intent.putExtra("title","背包问题");
                intent.putExtra("content","从N件价值与重量不同的物品中选取部分物品，使选中物品总量不超过限制重量且价值和最大。");
                intent.putExtra("position",3);
                startActivity(intent);
            }
        });
        CardView cardView3 = (CardView) view.findViewById(R.id.best_series_4);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivitySeriesContent.class);
                intent.putExtra("title","汉诺塔问题");
                intent.putExtra("content","神创造世界的时候做了三根金刚石柱子，在一根柱子上从下往上按照大小顺序摞着64片黄金圆盘。神命令婆罗门把圆盘从下面开始按大小顺序重新摆放在另一根柱子上。并且规定，在小圆盘上不能放大圆盘，在三根柱子之间一次只能移动一个圆盘。请问要移动多少次。");
                intent.putExtra("position",4);
                startActivity(intent);
            }
        });

        CardView data_structure = (CardView) view.findViewById(R.id.data_structure);
        data_structure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityAllSeries.class);
                startActivity(intent);
            }
        });

        return  view;
    }

}
