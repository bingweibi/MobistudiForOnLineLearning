package czd.mobistudi.md.ui.dialog;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import chinaykc.mobistudi.R;
import czd.mobistudi.md.model.storage.SettingShared;

public final class AlertDialogUtils {

    private AlertDialogUtils() {}

    public static AlertDialog.Builder createBuilderWithAutoTheme(@NonNull Activity activity) {
        return new AlertDialog.Builder(activity, SettingShared.isEnableThemeDark(activity) ? R.style.AppDialogDark_Alert : R.style.AppDialogLight_Alert);
    }

}
