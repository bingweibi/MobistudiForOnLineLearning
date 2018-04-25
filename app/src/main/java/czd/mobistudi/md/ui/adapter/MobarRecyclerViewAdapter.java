package czd.mobistudi.md.ui.adapter;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chinaykc.mobistudi.R;
import czd.mobistudi.mo.activity.BlogDetailActivity;
import czd.mobistudi.md.model.entity.BlogListBean;
import czd.mobistudi.md.model.glide.GlideApp;

public class MobarRecyclerViewAdapter extends RecyclerView.Adapter<MobarRecyclerViewAdapter.ViewHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    private View mHeaderView;
    private View mFooterView;

    private final Activity activity;
    private final LayoutInflater inflater;
    private final List<BlogListBean> blogList = new ArrayList<>();

    public MobarRecyclerViewAdapter(@NonNull Activity activity) {
        this.activity = activity;
//        inflater = LayoutInflater.from(activity);
        inflater = activity.getLayoutInflater();
    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getFooterView() {
        return mFooterView;
    }
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount()-1);
    }


    public void clearBlogListAndNotify() {
        blogList.clear();
        notifyDataSetChanged();
    }

    public void setBlogListAndNotify(@NonNull List<BlogListBean> blogList) {
        this.blogList.clear();
        this.blogList.addAll(blogList);
        notifyDataSetChanged();
    }

    public void appendTopicListAndNotify(@NonNull List<BlogListBean> blogList) {
        int startPosition = this.blogList.size();
        this.blogList.addAll(blogList);
        notifyItemRangeInserted(startPosition, blogList.size());
    }

    @Override
    public int getItemCount() {
        if(mHeaderView == null && mFooterView == null){
            return blogList.size();
        }else if(mHeaderView == null && mFooterView != null){
            return blogList.size() + 1;
        }else if (mHeaderView != null && mFooterView == null){
            return blogList.size() + 1;
        }else {
            return blogList.size() + 2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null){
            return TYPE_NORMAL;
        }
        if (position == 0){
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount()-1){
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        }
        if(mFooterView != null && viewType == TYPE_FOOTER){
            return new ViewHolder(mFooterView);
        }
        View view = inflater.inflate(R.layout.list_item_card_big, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){
            //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
            if(mHeaderView == null){
                holder.bind(blogList.get(position));
            }else{
                holder.bind(blogList.get(position-1));
            }
        }else if(getItemViewType(position) == TYPE_HEADER){
            return;
        }else{
            return;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_summary)
        TextView tvSummary;

        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;

        @BindView(R.id.tv_category)
        TextView tvCategory;

        @BindView(R.id.button)
        Button mButton;

        private BlogListBean mBlog;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (itemView == mHeaderView){
                return;
            }
            if (itemView == mFooterView){
                return;
            }
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull BlogListBean blog) {
            mBlog = blog;
            GlideApp.with(activity).load(blog.getImage()).placeholder(R.drawable.image_placeholder).into(imageView);
            tvTitle.setText(blog.getTitle());
            tvSummary.setText(blog.getSummary());
            tvCreateTime.setText(blog.getTime());
            tvCategory.setText(blog.getCategory());
        }

        @OnClick({R.id.imageView,R.id.tv_title,R.id.button})
        void onBtnClick() {
            Intent intent = new Intent(activity, BlogDetailActivity.class);
            intent.putExtra("url",mBlog.getHref());
            activity.startActivity(intent);
        }
    }

}
