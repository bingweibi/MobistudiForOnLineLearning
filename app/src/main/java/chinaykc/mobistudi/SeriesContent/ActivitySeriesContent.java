package chinaykc.mobistudi.SeriesContent;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chinaykc.mobistudi.R;


/**
 * @author ykc
 * 系列->系列的内容
 * 系列->数据结构->点击某个系列
 */
public class ActivitySeriesContent extends AppCompatActivity {

    private List<SeriesContent> seriesContentList = new ArrayList<>();
    private SeriesContentAdapter adapter;
    private SeriesContent[][] seriesContents = {
            {
                new SeriesContent(0, 1, 1, "01:00", "骑士周游问题"),
                new SeriesContent(0, 2, 2, "01:00", "图的基本概念"),
                new SeriesContent(0, 3, 3, "01:00", "邻接矩阵")},
            {
                    new SeriesContent(0, 4, 4, "01:00", "邻接表"),
                    new SeriesContent(0, 5, 5, "01:00", "十字链表")},
            {
                new SeriesContent(0, 6, 6, "01:00", "循环链表"),
                new SeriesContent(0, 7, 7, "01:00", "约瑟夫环"),
                new SeriesContent(0, 8, 8, "01:00", "栈的顺序存储")},
            {
                new SeriesContent(0, 6, 6, "01:00", "递归"),
                new SeriesContent(0, 7, 7, "01:00", "回溯算法解决八皇后问题")},
            {
                new SeriesContent(0, 6, 6, "01:00", "广度优先遍历"),
                new SeriesContent(0, 7, 7, "01:00", "深度优先遍历"),
                new SeriesContent(0, 8, 8, "01:00", "递归")},
            {
                new SeriesContent(0, 6, 6, "01:00", "队列的数组实现"),
                new SeriesContent(0, 7, 7, "01:00", "队列的链表实现")}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_content);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        TextView title =  findViewById(R.id.xilietiaozhuan_title);
        title.setText(getIntent().getStringExtra("title"));
        TextView content =  findViewById(R.id.xilietiaozhuan_content);
        content.setText(getIntent().getStringExtra("content"));
        initSeriesContent();
        RecyclerView recyclerView =  findViewById(R.id.series_content_recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SeriesContentAdapter(seriesContentList);
        recyclerView.setAdapter(adapter);
    }

    private void initSeriesContent() {
        seriesContentList.clear();

        int a = getIntent().getIntExtra("position", 0);
        seriesContentList.addAll(Arrays.asList(seriesContents[a]));
    }
}
