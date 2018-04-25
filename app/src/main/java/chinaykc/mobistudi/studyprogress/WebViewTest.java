package chinaykc.mobistudi.studyprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import chinaykc.mobistudi.R;

/**
 * @author ykc
 */
public class WebViewTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_test);
        WebView webview = (WebView) findViewById(R.id.webview1);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://www.earofsky.com/home/Kmap/index?kunitId=62");

    }
}
