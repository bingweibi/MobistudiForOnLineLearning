package czd.mobistudi.md.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import chinaykc.mobistudi.R;
import czd.mobistudi.md.ui.activity.base.StatusBarActivity;
import czd.mobistudi.md.ui.listener.NavigationFinishClickListener;
import czd.mobistudi.md.ui.util.ThemeUtils;
import czd.mobistudi.md.ui.widget.PreviewWebView;
import czd.mobistudi.md.util.FormatUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarkdownPreviewActivity extends StatusBarActivity {

    private static final String EXTRA_MARKDOWN = "markdown";

    public static void start(@NonNull Activity activity, String markdown) {
        Intent intent = new Intent(activity, MarkdownPreviewActivity.class);
        intent.putExtra(EXTRA_MARKDOWN, markdown);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.web_preview)
    PreviewWebView webPreview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdown_preview);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        String markdown = getIntent().getStringExtra(EXTRA_MARKDOWN);
        webPreview.loadRenderedContent(FormatUtils.handleHtml(FormatUtils.renderMarkdown(markdown)).body().html());
    }

    @Override
    public void onBackPressed() {
        if (webPreview.canGoBack()) {
            webPreview.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
