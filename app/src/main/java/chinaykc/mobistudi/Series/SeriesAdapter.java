package chinaykc.mobistudi.Series;

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
import chinaykc.mobistudi.SeriesContent.ActivitySeriesContent;

/**
 * Created by china on 2017/9/26.
 * @author ykc
 */

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {

    private static final String TAG = "SeriesAdapter";

    private Context mContext;
    private List<Series> mSeriesList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView seriesTitle;
        TextView seriesContent;
        TextView seriesLike;
        TextView seriesVideoNumber;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            seriesTitle = (TextView) view.findViewById(R.id.series_title);
            seriesContent = (TextView) view.findViewById(R.id.series_content);
            seriesLike = (TextView) view.findViewById(R.id.series_like);
            seriesVideoNumber = (TextView) view.findViewById(R.id.series_video_number);
        }
    }

    public SeriesAdapter(List<Series> seriesList) {
        mSeriesList = seriesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.all_series_item, parent, false);
        final ViewHolder holder=new ViewHolder(view);
        holder.seriesContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Series series=mSeriesList.get(position);
                //Toast.makeText(v.getContext(),series.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mContext, ActivitySeriesContent.class);
                intent.putExtra("title",series.getTitle());
                intent.putExtra("content",series.getContent());
                intent.putExtra("position",position);
                mContext.startActivity(intent);
            }
        });
        holder.seriesLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postition=holder.getAdapterPosition();
                Series series=mSeriesList.get(postition);
                holder.seriesLike.setText(Long.toString(series.getAddLongLike())+"❤");
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Series series = mSeriesList.get(position);
        holder.seriesTitle.setText(series.getTitle());
        holder.seriesContent.setText(series.getContent());
        holder.seriesVideoNumber.setText(series.getVideo_number());
        holder.seriesLike.setText(series.getLike());
    }

    @Override
    public int getItemCount() {
        return mSeriesList.size();
    }
}
