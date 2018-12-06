package com.dong.wanandroid.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseActivity;
import com.dong.wanandroid.ui.activity.login.LoginActivity;
import com.dong.wanandroid.model.user.UserModel;
import com.dong.wanandroid.presenter.register.IPresenter;
import com.dong.wanandroid.presenter.register.IpresenterCompl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements IRegister {
    private static final String TAG = "RegisterActivity";
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_repassword)
    EditText etRepassword;
    @BindView(R.id.button)
    Button btnRegister;
    private IPresenter ipresenterCompl;
    private String userName;
    private String pwdstr;
    private String repwdstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        ipresenterCompl = new IpresenterCompl(this,this);
    }



    @OnClick(R.id.button)
    public void onViewClicked() {
        userName = etUsername.getText().toString().trim();
        pwdstr = etPassword.getText().toString().trim();
        repwdstr = etRepassword.getText().toString().trim();
        //判断输入内容不能为空
        if (!TextUtils.isEmpty(userName)&&!TextUtils.isEmpty(pwdstr)&&!TextUtils.isEmpty(repwdstr)){
            //调用逻辑层的登录
            ipresenterCompl.register(userName,pwdstr,repwdstr);
        }else{
            Toast.makeText(this,"还有选项未填哟~",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void registerResult(int resultCode, String msg, UserModel userModle) {
        if (resultCode == 0) {
            // 发送给登录页面，如果存在直接再登录页面填写用户名
//            LogUtils.eTag("222","注册成功的username---->"+userModle.getUsername());
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
