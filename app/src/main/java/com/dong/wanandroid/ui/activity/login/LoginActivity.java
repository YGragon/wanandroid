package com.dong.wanandroid.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseActivity;
import com.dong.wanandroid.db.DBHelper;
import com.dong.wanandroid.event_bus_model.UserEvent;
import com.dong.wanandroid.model.user.UserModel;
import com.dong.wanandroid.presenter.login.IPresenter;
import com.dong.wanandroid.presenter.login.IpresenterCompl;
import com.dong.wanandroid.ui.activity.register.RegisterActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginView {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.register_btn)
    Button btnRegister;
    @BindView(R.id.login_btn)
    Button btnLogin;
    @BindView(R.id.loading_view)
    ProgressBar loadingView;

    private IPresenter iPresenter;
    private String use;
    private String pwdstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        iPresenter = new IpresenterCompl(this,this);
    }

    @OnClick({R.id.login_btn,R.id.register_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_btn:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login_btn:
                use = etUsername.getText().toString().trim();
                pwdstr = etPassword.getText().toString().trim();
                //判断输入内容不能为空
                if (!TextUtils.isEmpty(use)&&!TextUtils.isEmpty(pwdstr)){
                    //调用逻辑层的登录
                    iPresenter.login(use,pwdstr);
                }else{
                    Toast.makeText(this,"还有信息未填写哟~",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void loginResult(int resultCode, String msg, UserModel userModle) {
        if (resultCode == 0) {
            Toast.makeText(this, "欢迎你"+userModle.getUsername(), Toast.LENGTH_SHORT).show();
            // 存储到数据库中
            DBHelper.setUserToDb(userModle);
            EventBus.getDefault().postSticky(new UserEvent(userModle));
            finish();
        }else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
