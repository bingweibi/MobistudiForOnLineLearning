package czd.mobistudi.md.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import org.cnodejs.android.oauthlogin.CNodeOAuthLoginActivity;

import chinaykc.mobistudi.R;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import czd.mobistudi.mo.activity.base.BaseActivity;
import czd.mobistudi.md.model.entity.Result;
import czd.mobistudi.md.model.storage.LoginShared;
import czd.mobistudi.md.presenter.contract.ILoginPresenter;
import czd.mobistudi.md.presenter.implement.LoginPresenter;
import czd.mobistudi.md.ui.dialog.AlertDialogUtils;
import czd.mobistudi.md.ui.dialog.ProgressDialog;
import czd.mobistudi.md.ui.listener.DialogCancelCallListener;
import czd.mobistudi.md.ui.util.ToastUtils;
import czd.mobistudi.md.ui.view.ILoginView;
import retrofit2.Call;


public class LoginActivity extends BaseActivity implements ILoginView {

    private static final int REQUEST_GITHUB_LOGIN = 1;
    public static final int REQUEST_DEFAULT = 0;

    @BindView(R.id.bt_go)
    Button btGo;

    private ProgressDialog progressDialog;
    private ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);

        progressDialog = ProgressDialog.createWithAutoTheme(this);
        loginPresenter = new LoginPresenter(this, this);
    }

    public static void startForResult(@NonNull Activity activity, int requestCode) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startForResult(@NonNull Activity activity) {
        startForResult(activity, REQUEST_DEFAULT);
    }

    public static boolean checkLogin(@NonNull final Activity activity, final int requestCode) {
        if (TextUtils.isEmpty(LoginShared.getAccessToken(activity))) {
            AlertDialogUtils.createBuilderWithAutoTheme(activity)
                    .setMessage(R.string.need_login_tip)
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startForResult(activity, requestCode);
                        }

                    })
                    .setNegativeButton(R.string.cancel, null)
                    .show();
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkLogin(@NonNull Activity activity) {
        return checkLogin(activity, REQUEST_DEFAULT);
    }

    @TargetApi(21)
    @OnClick(R.id.bt_go)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_go:
                Explode explode = new Explode();
                explode.setDuration(500);

                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
                Intent i2 = new Intent(this, czd.mobistudi.mo.activity.MobarActivity.class);
                startActivity(i2, oc2.toBundle());
                finish();
                break;
        }
    }

    @OnClick(R.id.btn_github_login)
    void onBtnGithubLoginClick() {
        startActivityForResult(new Intent(this, CNodeOAuthLoginActivity.class), REQUEST_GITHUB_LOGIN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_GITHUB_LOGIN) {
                loginPresenter.loginAsyncTask(data.getStringExtra(CNodeOAuthLoginActivity.EXTRA_ACCESS_TOKEN));
            }
        }catch(NullPointerException e){
            Log.d("LoginActiviry", "NullPointerError: " + e);
        }
    }

    @Override
    public void onAccessTokenError(@NonNull String message) {

    }

    @Override
    public void onLoginOk(@NonNull String accessToken, @NonNull Result.Login loginInfo) {
        LoginShared.login(this, accessToken, loginInfo);
        ToastUtils.with(this).show(R.string.login_success);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onLoginStart(@NonNull Call<Result.Login> call) {
        progressDialog.setOnCancelListener(new DialogCancelCallListener(call));
        progressDialog.show();
    }

    @Override
    public void onLoginFinish() {
        progressDialog.setOnCancelListener(null);
        progressDialog.dismiss();
    }

}
