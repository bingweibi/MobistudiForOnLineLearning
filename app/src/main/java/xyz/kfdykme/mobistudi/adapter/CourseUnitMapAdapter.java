package xyz.kfdykme.mobistudi.adapter;

import android.content.Context;
import android.content.Intent;
import android.widget.*;
import android.support.v7.widget.*;
import android.view.*;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.activity.CourseDetailActivity;
import xyz.kfdykme.mobistudi.bean.StudyCourse;
import xyz.kfdykme.mobistudi.eventbus.CourseDetailEvent;

/**
 * @author cxf
 */
public class CourseUnitMapAdapter extends RecyclerView.Adapter<CourseUnitMapAdapter.CourseViewHolder>{

	private List<StudyCourse> mDatas;
	private LayoutInflater Inflater;
	private Context context;

	public CourseUnitMapAdapter(Context context, List<StudyCourse> mDatas){
		this.mDatas = mDatas;
		this.context = context;
		Inflater = LayoutInflater.from(context);
	}
	
	@Override
	public CourseUnitMapAdapter.CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = Inflater.inflate(R.layout.rv_item_course_unit_map,parent,false);
		CourseViewHolder indexViewHolder = new CourseViewHolder(view);
		return indexViewHolder;
	}

	@Override
	public void onBindViewHolder(CourseUnitMapAdapter.CourseViewHolder indexViewHolder, final int position){
		indexViewHolder.view.setText(mDatas.get(position).getTitle());
		indexViewHolder.view.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View view)
				{
					Intent intent = new Intent(context,CourseDetailActivity.class);
					context.startActivity(intent);
					EventBus.getDefault().postSticky(new CourseDetailEvent(mDatas.get(position)));
				}
				
			
		});
	}

	@Override
	public int getItemCount(){
		// TODO: Implement this method
		return mDatas.size();
	}

	public void addData(List<StudyCourse> datas){
		mDatas.addAll(datas);
		notifyDataSetChanged();
	}
	
	class CourseViewHolder extends RecyclerView.ViewHolder{
		TextView view;
		CourseViewHolder(View view){
			super(view);
			this.view = (TextView)view.findViewById(R.id.rv_item_course_unit_map_textview);
		}
	}
}
