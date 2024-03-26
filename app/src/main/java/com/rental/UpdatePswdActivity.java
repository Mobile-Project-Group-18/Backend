package com.rental;

import net.tsz.afinal.http.AjaxParams;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.base.BaseActivity;
import com.wang.config.Consts;
import com.wang.db.MemberUserUtils;
import com.wang.model.ResponseEntry;
import com.wang.util.ToastUtil;


/**
 */
public class UpdatePswdActivity extends BaseActivity {

	// title
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;
	// 查询按钮
	private Button mbtnUpdate;
	private EditText metOldPswd;
	private EditText metNewPswd;
	private EditText mettwoPswd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_pswd);
		initWidget();
	}

	@Override
	public void initWidget() {
		metOldPswd = (EditText) findViewById(R.id.metOldPswd);
		metNewPswd = (EditText) findViewById(R.id.metNewPswd);
		mettwoPswd = (EditText) findViewById(R.id.mettwoPswd);
		mbtnUpdate = (Button) findViewById(R.id.mbtnUpdate);
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mTvTitle.setText("change password");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mbtnUpdate.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.mIvBack:
				UpdatePswdActivity.this.finish();
				break;
			case R.id.mbtnUpdate:

				if (TextUtils.isEmpty(metOldPswd.getText().toString())) {
					ToastUtil.ShowCentre(UpdatePswdActivity.this, "please enter the current password");
					return;
				}

				
				if (TextUtils.isEmpty(metNewPswd.getText().toString())) {
					ToastUtil.ShowCentre(UpdatePswdActivity.this, "please enter the new password");
					return;
				}
				
				if (TextUtils.isEmpty(mettwoPswd.getText().toString())) {
					ToastUtil.ShowCentre(UpdatePswdActivity.this, "please enter the new password again");
					return;
				}
				RegisterAction(true);
				break;
		}
	}

	@Override
	public void initData() {

	}

	private void RegisterAction(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "updatePswd");
		params.put("upswd", metNewPswd.getText().toString());
		params.put("uid", MemberUserUtils.getUid(this));
		httpPost(Consts.URL + Consts.APP.RegisterAction, params, Consts.actionId.resultFlag, isShow, "updating...");
	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);
		ToastUtil.show(UpdatePswdActivity.this, entry.getRepMsg());

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				UpdatePswdActivity.this.finish();
			}
		}, 1000);
	}

	@Override
	protected void callBackAllFailure(String strMsg, int actionId) {
		super.callBackAllFailure(strMsg, actionId);
		ToastUtil.show(UpdatePswdActivity.this, strMsg);

	}
}
