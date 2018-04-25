package czd.mobistudi.md.ui.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import chinaykc.mobistudi.R;
import czd.mobistudi.md.model.entity.Topic;
import czd.mobistudi.md.model.glide.GlideApp;
import czd.mobistudi.md.ui.activity.UserDetailActivity;

import czd.mobistudi.md.ui.util.Navigator;
import czd.mobistudi.md.util.FormatUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thugwar on 2018/3/12.
 */

public class SecondRecyclerViewAdapter  extends RecyclerView.Adapter<SecondRecyclerViewAdapter.ViewHolder> {
    private final List<Topic> topicList;
    private final Activity activity;

    public SecondRecyclerViewAdapter(Activity activity) {
        this.activity = activity;
        this.topicList = new ArrayList<>();
    }

    public void clearTopicListAndNotify() {
        topicList.clear();
        notifyDataSetChanged();
    }

    public void setTopicListAndNotify(@NonNull List<Topic> topicList) {
        this.topicList.clear();
        this.topicList.addAll(topicList);
        //
        if(topicList.get(0).isTop()){
            this.topicList.remove(0);
        }
        notifyDataSetChanged();
    }

    public void appendTopicListAndNotify(@NonNull List<Topic> topicList) {
        int startPosition = this.topicList.size();
        this.topicList.addAll(topicList);
        notifyItemRangeInserted(startPosition, topicList.size());
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    @Override
    public SecondRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(activity)
//                .inflate(R.layout.item_topic_card, parent, false);
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_card, parent, false);
        SecondRecyclerViewAdapter.ViewHolder mViewHolder = new SecondRecyclerViewAdapter.ViewHolder(view);

        return mViewHolder;
    }


    @Override
    public void onBindViewHolder(SecondRecyclerViewAdapter.ViewHolder holder, int position) {
        Topic topic = topicList.get(position);
        holder.ctvTab.setText(topic.isTop() ? R.string.tab_top : topic.getTab().getNameId());
        holder.ctvTab.setChecked(topic.isTop());
        holder.tvReplyCount.setText(String.valueOf(topic.getReplyCount()));
        holder.tvVisitCount.setText(String.valueOf(topic.getVisitCount()));
        holder.tvLastReplyTime.setText(FormatUtils.getRelativeTimeSpanString(topic.getLastReplyAt()));
        holder.tvTitle.setText(topic.getTitle());
        holder.tvSummary.setText(topic.getContentSummary());
        GlideApp.with(activity).load(topic.getAuthor().getAvatarUrl()).placeholder(R.drawable.image_placeholder).into(holder.imgAvatar);
        holder.tvAuthor.setText(topic.getAuthor().getLoginName());
        holder.tvCreateTime.setText(activity.getString(R.string.create_at__, topic.getCreateAt().toString("yyyy-MM-dd HH:mm:ss")));
        holder.iconGood.setVisibility(topic.isGood() ? View.VISIBLE : View.GONE);
        holder.topic = topicList.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CheckedTextView ctvTab;
        TextView tvReplyCount;
        TextView tvVisitCount;
        TextView tvLastReplyTime;
        TextView tvTitle;
        TextView tvSummary;
        ImageView imgAvatar;
        TextView tvAuthor;
        TextView tvCreateTime;
        View iconGood;
        LinearLayout btnTopic;
        LinearLayout btnUser;
        Topic topic;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ctvTab = itemView.findViewById(R.id.ctv_tab);
            tvReplyCount = itemView.findViewById(R.id.tv_reply_count);
            tvVisitCount = itemView.findViewById(R.id.tv_visit_count);
            tvLastReplyTime = itemView.findViewById(R.id.tv_last_reply_time);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSummary = itemView.findViewById(R.id.tv_summary);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvCreateTime = itemView.findViewById(R.id.tv_create_time);
            iconGood = itemView.findViewById(R.id.icon_good);
            btnTopic = itemView.findViewById(R.id.btn_topic);
            btnUser = itemView.findViewById(R.id.btn_user);
            btnTopic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigator.TopicWithAutoCompat.start(activity, topic);
                }
            });
            btnUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserDetailActivity.startWithTransitionAnimation(activity, topic.getAuthor().getLoginName(), imgAvatar, topic.getAuthor().getAvatarUrl());
                }
            });
        }
    }
}
