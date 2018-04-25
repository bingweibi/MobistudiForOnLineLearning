package chinaykc.mobistudi.AddedCourse;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import chinaykc.mobistudi.R;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author ykc
 */
public class AddedCourseAdapter extends RecyclerView.Adapter<AddedCourseAdapter.ViewHolder2>{

    private static final String TAG = "AddedCourseAdapter";

    private Context mContext;

    private List<AddedCourse> mAddedCourseList;

    static class ViewHolder2 extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView Image;
        TextView Name;

        public ViewHolder2(View view) {
            super(view);
            cardView = (CardView) view;
            Image = (ImageView) view.findViewById(R.id.added_course_image);
            Name = (TextView) view.findViewById(R.id.added_course_name);
        }
    }

    public AddedCourseAdapter(List<AddedCourse> addedCourseList) {
        mAddedCourseList = addedCourseList;
    }

    @Override
    public ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.added_course_item, parent, false);

        return new ViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder2 holder, int position) {
        AddedCourse addedCourse = mAddedCourseList.get(position);
        holder.Name.setText(addedCourse.getName());
 //       Glide.with(mContext).load(addedCourse.getImageId()).into(holder.fruitImage);
        Glide.with(mContext)
                .load(addedCourse.getImageId())
//                .crossFade(1000)
//                .bitmapTransform(new BlurTransformation(mContext,15,2)) // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                .into(holder.Image);
    }

    @Override
    public int getItemCount() {
        return mAddedCourseList.size();
    }

}
