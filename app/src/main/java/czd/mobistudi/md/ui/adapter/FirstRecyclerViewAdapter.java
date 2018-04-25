package czd.mobistudi.md.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import chinaykc.mobistudi.R;

import java.util.List;
import java.util.Random;

import czd.mobistudi.mo.activity.BlankActivity;
import chinaykc.mobistudi.HotCourse;

public class FirstRecyclerViewAdapter extends RecyclerView.Adapter<FirstRecyclerViewAdapter.MyViewHolder> {

    private List<Object> contents;
    private Context mContext;

    private int layoutType;
    private int[] itemTypeArray;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CELL = 1;

    public static final int LAYOUT_LINEAR = 0;
    public static final int LAYOUT_GRID = 1;
    public static final int LAYOUT_LINEAR_MIX = 2;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView Image;
        TextView Name;

        MyViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            Image = (ImageView) view.findViewById(R.id.hot_course_image);
            Name = (TextView) view.findViewById(R.id.hot_course_name);
        }
    }

    public void initialItemTypeArray(){
        final Random random = new Random();

        for(int i=1;i<contents.size();i++){
            int randType = random.nextInt(2);
            itemTypeArray[i] = randType;
            if(randType == TYPE_CELL && i<contents.size()-1){
                itemTypeArray[++i] = TYPE_CELL;
            }
        }
    }

    public int getLayoutType() {
        return layoutType;
    }

    public FirstRecyclerViewAdapter(List<Object> contents, int layoutType) {
        this.contents = contents;
        this.layoutType = layoutType;
        if(layoutType == LAYOUT_LINEAR_MIX){
            itemTypeArray = new int[contents.size()];
            itemTypeArray[0] = TYPE_HEADER;
            initialItemTypeArray();
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(layoutType == LAYOUT_LINEAR){
            switch (position) {
                case 0:
                    return TYPE_HEADER;
                default:
                    return TYPE_CELL;
            }
        }else if(layoutType == LAYOUT_GRID){
            switch (position) {
                case 0:case 1:
                    return TYPE_HEADER;
                default:
                    return TYPE_CELL;
            }
        }else { //layoutType == LAYOUT_LINEAR_MIX
           return itemTypeArray[position];
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;

        if (mContext == null) {
            mContext = parent.getContext();
        }

        switch (viewType) {
            case TYPE_HEADER:
                mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.hot_courses_item_card_big, parent, false);
            break;
            case TYPE_CELL:default:
                mView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.hot_courses_item_card_small, parent, false);
                break;
        }

        final MyViewHolder mViewHolder = new MyViewHolder(mView);
        mViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int position = mViewHolder.getAdapterPosition();
                //HotCourse hotCourse = mHotCourseList.get(position);
                Intent intent = new Intent(mContext, BlankActivity.class);
                //intent.putExtra(, hotCourse.getName());
                //intent.putExtra(, hotCourse.getImageId());
                mContext.startActivity(intent);

            }
        });

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(FirstRecyclerViewAdapter.MyViewHolder holder, int position) {
        HotCourse hotCourse;
        switch (getItemViewType(position)) {
            case TYPE_HEADER:case TYPE_CELL:default:
                hotCourse = (HotCourse)contents.get(position);
                holder.Name.setText(hotCourse.getName());
                Glide.with(mContext).load(hotCourse.getImageId()).into(holder.Image);
                break;
        }
    }
}