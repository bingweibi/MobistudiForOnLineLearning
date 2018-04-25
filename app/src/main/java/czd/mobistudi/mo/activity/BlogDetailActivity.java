package czd.mobistudi.mo.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import chinaykc.mobistudi.R;
import czd.mobistudi.md.model.entity.BlogDetailBean;
import czd.mobistudi.md.model.manager.BlogDetailDataManager;
import czd.mobistudi.md.model.manager.OkhttpManager;
import okhttp3.Request;

import static android.webkit.WebSettings.LOAD_DEFAULT;

public class BlogDetailActivity extends AppCompatActivity {
    private WebView mWebView;
    private Toolbar mToolbar;
    private String  loadUrl;
    private NestedScrollView mScrollView;
    private ProgressBar mProgressBar;
    StringBuilder htmlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);
        loadUrl = getIntent().getStringExtra("url");

        bindViews();
    }

    private void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mScrollView.scrollTo(0,0);
                return true;
            }
        });
        mWebView = findViewById(R.id.blog_detail_webview);
        mScrollView = findViewById(R.id.scrollView);
        mProgressBar = findViewById(R.id.progress_bar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "评论", Snackbar.LENGTH_SHORT)
                        .setAction("刷新", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                       mWebView.reload();
                                    }
                                }
                        ).show();
            }
        });


        mWebView.setWebChromeClient(new WebChromeClient() {
            //这里设置获取到的网站title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mToolbar.setTitle(title);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            //在webview里打开新链接
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void  onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
                if(mProgressBar.getVisibility() == View.GONE){
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                if(mScrollView.getVisibility() == View.VISIBLE){
                    mScrollView.setVisibility(View.GONE);
                }
                Log.d("BlogLoad", "onPageStarted: ");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作
                if(mProgressBar.getVisibility() == View.VISIBLE){
                    mProgressBar.setVisibility(View.GONE);
                }
                if(mScrollView.getVisibility() == View.GONE){
                    mScrollView.setVisibility(View.VISIBLE);
                }

                Log.d("BlogLoad", "onPageFinished: ");
            }

        });

        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);   //自适应屏幕
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false); //取消缩放控件
        settings.setSupportZoom(true);//设定支持缩放
//        settings.setAppCacheEnabled(true); //设置H5的缓存打开,默认关闭
        settings.setCacheMode(LOAD_DEFAULT);
//        if (mWebView.isHardwareAccelerated()) {
//            settings.setJavaScriptEnabled(true);
//        }

//        getHtmlContent(loadUrl);
        mWebView.loadUrl(loadUrl);
    }

    private void getHtmlContent(final String url){

        OkhttpManager.getAsync(url, new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                Log.d("BlogDetailActiity","URL load fail...");
            }
            @Override
            public void requestSuccess(String result) {
                Document document= Jsoup.parse(result, url);
                BlogDetailBean mBlogDetailBean = BlogDetailDataManager.getBlogDetailBean(document);
                if(mBlogDetailBean!=null){
                    htmlContent = new StringBuilder();
                    htmlContent.append("<html><head><title>");
                    htmlContent.append(mBlogDetailBean.getTitle());
                    htmlContent.append("</title>/head><body>");
                    htmlContent.append(mBlogDetailBean.getContent());
                    htmlContent.append("</body></html>");
                    mWebView.loadDataWithBaseURL(loadUrl, htmlContent.toString(), "text/html", "utf-8", null);
                }else{
                    Log.d("MobarFragment","NULL BlogListBeans");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_detail,menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {
        mWebView.stopLoading(); //停止加载
        ((ViewGroup) mWebView.getParent()).removeView(mWebView); //把webview从视图中移除
        mWebView.removeAllViews(); //移除webview上子view
//        mWebView.clearCache(true); //清除缓存
        mWebView.clearHistory(); //清除历史
        mWebView.destroy(); //销毁webview自身
//        Process.killProcess(Process.myPid()); //杀死WebView所在的进程
        super.onDestroy();
    }


}
