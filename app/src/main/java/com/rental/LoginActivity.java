package com.rental;

import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wang.base.BaseActivity;
import com.wang.config.Consts;
import com.wang.db.MemberUserUtils;
import com.wang.model.ResponseEntry;
import com.wang.model.UserModel;
import com.wang.util.LoadingDialog;
import com.wang.util.ToastUtil;

/**
 * 登录页面
 * 
 * @author WangKui
 * 
 */

public class LoginActivity extends BaseActivity {

	// title
	private TextView mTvTitle;
	// 登录用户名称
	private EditText mLoginNumber;
	// 登录密码
	private EditText mLoginPswd;
	// 登录按钮
	private Button mLogin;
	private Button mEnterpriseQuery;
	private LinearLayout mllTop;
	private ImageView mIvBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initWidget();
	}

	/**
	 * 控件初始化
	 */
	@Override
	public void initWidget() {
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mdialog = new LoadingDialog(this, "Is logining");
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mTvTitle.setText("Login");
		mLoginNumber = (EditText) findViewById(R.id.mLoginNumber);
		mLoginPswd = (EditText) findViewById(R.id.mLoginPswd);
		mLogin = (Button) findViewById(R.id.mLogin);
		mEnterpriseQuery = (Button) findViewById(R.id.mEnterpriseQuery);
		// mLoginNumber.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		// 事件的监听
		mLogin.setOnClickListener(this);
		mEnterpriseQuery.setOnClickListener(this);
		// 给输入框设置默认的测试数据
		mLoginNumber.setSelection(mLoginNumber.getText().length());
//		// mLoginNumber.setText("TEA20170123164556");
//		mLoginNumber.setText("15249245656");
//		mLoginPswd.setText("123456");
		
		mIvBack.setVisibility(View.GONE);
		mIvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.mLogin:
			if (TextUtils.isEmpty(mLoginNumber.getText().toString())) {
				ToastUtil.ShowCentre(LoginActivity.this, "please enter your mobile phone number");
				return;
			}
			if (TextUtils.isEmpty(mLoginPswd.getText().toString())) {
				ToastUtil.ShowCentre(LoginActivity.this, "please enter your password");
				return;
			}
			LoginUserPost(true);
			//
			break;
			
		case R.id.mIvBack:
			finish();
			break;
		case R.id.mEnterpriseQuery:
			Intent mEnterpriseQuery = new Intent(LoginActivity.this, RegisterCreatActivity.class);
			startActivity(mEnterpriseQuery);
		default:
			break;
		}
	}

	@Override
	public void initData() {
	}


	/**
	 * 用户的登录
	 * 
	 * @param isShow
	 */
	private void LoginUserPost(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "login");
		params.put("uphone", mLoginNumber.getText().toString());
		params.put("pswd", mLoginPswd.getText().toString());
		httpPost(Consts.URL + Consts.APP.RegisterAction, params, Consts.actionId.resultFlag, isShow, "Is logining...");
	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);

		switch (actionId) {
		case Consts.actionId.resultFlag:

			if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {
				UserModel userModel = mGson.fromJson(entry.getData(), UserModel.class);
				MemberUserUtils.setUid(LoginActivity.this, userModel.getUid());
				MemberUserUtils.setName(LoginActivity.this, userModel.getUname());
				MemberUserUtils.putBean(LoginActivity.this, "user_messgae", userModel);
				Intent intent = new Intent(LoginActivity.this, FrameworkActivity.class);
				startActivity(intent);
				finish();

			}
			break;

		}

	}

	@Override
	protected void callBackAllFailure(String strMsg, int actionId) {
		super.callBackAllFailure(strMsg, actionId);

		ToastUtil.show(LoginActivity.this, strMsg);

	}
}
