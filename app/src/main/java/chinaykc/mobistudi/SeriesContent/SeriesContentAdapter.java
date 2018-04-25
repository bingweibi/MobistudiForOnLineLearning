package chinaykc.mobistudi.SeriesContent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chinaykc.mobistudi.R;
import xyz.kfdykme.mobistudi.activity.CourseDetailActivity;

/**
 * Created by china on 2017/9/26.
 * @author ykc
 */

public class SeriesContentAdapter extends RecyclerView.Adapter<SeriesContentAdapter.ViewHolder> {

    private Context mContext;
    private List<SeriesContent> mSeriesContentList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView seriesContentTitle;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.series_content_card_view);
            seriesContentTitle = (TextView) view.findViewById(R.id.series_content_title);
        }
    }

    public SeriesContentAdapter(List<SeriesContent> seriesContentList) {
        mSeriesContentList = seriesContentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.series_content_item, parent, false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                SeriesContent seriesContent=mSeriesContentList.get(position);
                Intent intent=new Intent(mContext, CourseDetailActivity.class);
                intent.putExtra("title",seriesContent.getTitle());
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SeriesContent seriesContent = mSeriesContentList.get(position);
        holder.seriesContentTitle.setText(seriesContent.getTitle());
    }

    @Override
    public int getItemCount() {
        return mSeriesContentList.size();
    }
}
