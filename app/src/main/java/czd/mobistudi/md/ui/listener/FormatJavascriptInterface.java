package czd.mobistudi.md.ui.listener;

import android.webkit.JavascriptInterface;

import czd.mobistudi.md.util.FormatUtils;
import org.joda.time.DateTime;

public final class FormatJavascriptInterface {

    public static final FormatJavascriptInterface instance = new FormatJavascriptInterface();
    public static final String NAME = "formatBridge";

    private FormatJavascriptInterface() {}

    @JavascriptInterface
    public String getRelativeTimeSpanString(String time) {
        return FormatUtils.getRelativeTimeSpanString(new DateTime(time));
    }

}
