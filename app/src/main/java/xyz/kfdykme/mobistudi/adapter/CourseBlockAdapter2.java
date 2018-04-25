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
 * @author bibingwei
 * @date 2017/12/22
 */

public class CourseBlockAdapter2 extends RecyclerView.Adapter<CourseBlockAdapter2.ViewHolder> {

    private List<StudyCourse> courses;
    private LayoutInflater Inflater;
    private Context context;
    private CourseBlockAdapter2.ItemClickListener mItemClickListener;
    private String[] color = {"#99FFFF","#66FFFF","#5DE8F1","#53D1E3","#4AB9D5","#41A2C7","#388BB9","#2E74AC","#255D9E",
            "#1C4690","#132E82","#091774","#000066"};
    private int temp = 0;

    public void setItemClickListener(CourseBlockAdapter2.ItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener;
    }

    public interface ItemClickListener{
        void onClick(View view, int position);
    }

    public CourseBlockAdapter2(Context context, List<StudyCourse> courses){
        this.context = context;
        this.courses  = courses;
        Inflater = LayoutInflater.from(context);
    }

    @Override
    public CourseBlockAdapter2.ViewHolder onCreateViewHolder(ViewGroup p1, int p2){

        View view = Inflater.inflate(R.layout.rv_item_course_block,p1,false);
        CourseBlockAdapter2.ViewHolder holder = new CourseBlockAdapter2.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CourseBlockAdapter2.ViewHolder holder, final int position){
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
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            temp++;
        }
    }

    public void upData(List<StudyCourse> courses){
        this.courses =courses;
        notifyDataSetChanged();
    }
}
