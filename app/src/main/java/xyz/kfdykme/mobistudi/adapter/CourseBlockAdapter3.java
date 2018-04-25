package xyz.kfdykme.mobistudi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.bean.StudyCourse;

/**
 *
 * @author bibingwei
 * @date 2017/12/25
 */

public class CourseBlockAdapter3 extends RecyclerView.Adapter<CourseBlockAdapter3.ViewHolder> {

    private List<StudyCourse> courses;
    private LayoutInflater Inflater;
    private Context context;
    private CourseBlockAdapter3.ItemClickListener mItemClickListener;
    private String[] color = {"#99FFFF","#66FFFF","#5DE8F1","#53D1E3","#4AB9D5","#41A2C7","#388BB9","#2E74AC","#255D9E",
            "#1C4690","#132E82","#091774","#000066"};
    private String[] name = {"绪论","队列","线性表","栈","串","数组","广义表","树","图","排序"};
    private int temp = 0;

    public void setItemClickListener(CourseBlockAdapter3.ItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener;
    }

    public interface ItemClickListener{
        void onClick(View view, int position);
    }

    public CourseBlockAdapter3(Context context, List<StudyCourse> courses){
        this.context = context;
        this.courses  = courses;
        Inflater = LayoutInflater.from(context);
    }

    @Override
    public CourseBlockAdapter3.ViewHolder onCreateViewHolder(ViewGroup p1, int p2){

        View view = Inflater.inflate(R.layout.rv_item_course_block,p1,false);
        CourseBlockAdapter3.ViewHolder holder = new CourseBlockAdapter3.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CourseBlockAdapter3.ViewHolder holder, final int position){
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItemClickListener != null){
                    mItemClickListener.onClick(view,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ViewHolder(View view){
            super(view);
            textView = (TextView)view.findViewById(R.id.rv_item_course_block_textview);
            textView.setBackgroundColor(Color.parseColor(color[temp]));
            textView.setTextSize(15);
            textView.setText(name[temp]);
            temp++;
        }
    }

    public void upData(List<StudyCourse> courses){
        this.courses =courses;
        notifyDataSetChanged();
    }
}
