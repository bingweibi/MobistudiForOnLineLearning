package chinaykc.mobistudi;

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

import java.util.List;

/**
 * @author ykc
 */
public class HotCourseAdapter extends RecyclerView.Adapter<HotCourseAdapter.ViewHolder>{

    private static final String TAG = "HotCourseAdapter";

    private Context mContext;

    private List<HotCourse> mHotCourseList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView Image;
        TextView Name;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            Image = (ImageView) view.findViewById(R.id.hot_course_image);
            Name = (TextView) view.findViewById(R.id.hot_course_name);
        }
    }

    public HotCourseAdapter(List<HotCourse> hotCourseList) {
        mHotCourseList = hotCourseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.hot_course_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                //HotCourse hotCourse = mHotCourseList.get(position);
                Intent intent = new Intent(mContext, Feedback.class);
                //intent.putExtra(, hotCourse.getUserName());
                //intent.putExtra(, hotCourse.getImageId());
                mContext.startActivity(intent);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HotCourse hotCourse = mHotCourseList.get(position);
        holder.Name.setText(hotCourse.getName());
        Glide.with(mContext).load(hotCourse.getImageId()).into(holder.Image);
    }

    @Override
    public int getItemCount() {
        return mHotCourseList.size();
    }

}
