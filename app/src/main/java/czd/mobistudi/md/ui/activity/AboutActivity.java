package czd.mobistudi.md.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import chinaykc.mobistudi.BuildConfig;
import chinaykc.mobistudi.R;
import czd.mobistudi.md.ui.activity.base.StatusBarActivity;
import czd.mobistudi.md.ui.listener.NavigationFinishClickListener;
import czd.mobistudi.md.ui.util.Navigator;
import czd.mobistudi.md.ui.util.ThemeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends StatusBarActivity {

    public static final String VERSION_TEXT = BuildConfig.VERSION_NAME + "-build-" + BuildConfig.VERSION_CODE;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.AppThemeLight, R.style.AppThemeDark);
        setFitsStatusBarMode();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        tvVersion.setText(VERSION_TEXT);
    }

    @OnClick(R.id.btn_version)
    void onBtnVersionClick() {
        // nothing to do
    }

    @OnClick(R.id.btn_open_source_url)
    void onBtnOpenSourceUrlClick() {
        Navigator.openInBrowser(this, getString(R.string.open_source_url_content));
    }

    @OnClick(R.id.btn_about_cnode)
    void onBtnAboutCNodeClick() {
        Navigator.openInBrowser(this, getString(R.string.about_cnode_content));
    }

    @OnClick(R.id.btn_about_author)
    void onBtnAboutAuthorClick() {
        Navigator.openInBrowser(this, getString(R.string.about_author_content));
    }

    @OnClick(R.id.btn_open_in_market)
    void onBtnOpenInMarketClick() {
        Navigator.openInMarket(this);
    }

    @OnClick(R.id.btn_advice_feedback)
    void onBtnAdviceFeedbackClick() {
        Navigator.openEmail(
                this,
                "mobistudi@foxmail.com",
                "来自 Mobistudi-" + VERSION_TEXT + " 的客户端反馈",
                "设备信息：Android " + Build.VERSION.RELEASE + " - " + Build.MANUFACTURER + " - " + Build.MODEL + "\n（如果涉及隐私请手动删除这个内容）\n\n"
        );
    }
}
