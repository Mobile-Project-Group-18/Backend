package com.rental;

import net.tsz.afinal.http.AjaxParams;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.wang.base.BaseActivity;
import com.wang.config.Consts;
import com.wang.db.MemberUserUtils;
import com.wang.model.HouseModel;
import com.wang.model.ResponseEntry;
import com.wang.model.UserModel;
import com.wang.util.ToastUtil;

public class PayMessageActivity extends BaseActivity {

	// 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;
	private TextView mtvPrice;
	private Button mPay;
	private int choiceType = 1;

	private TextView mtvName;
	UserModel userModel;
	private EditText metMessage;
	private HouseModel shopModel;
	private RadioGroup mrdChoice;
	private RadioButton mrbWeiXin = null;
	private RadioButton mrbPay = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_message);
		initWidget();
		initData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mIvBack:
			finish();
			break;
		case R.id.mPay:
			OrderAction(true);
			break;
		}
	}

	@Override
	public void initWidget() {
		mtvName = (TextView) findViewById(R.id.mtvName);
		mrdChoice = (RadioGroup) findViewById(R.id.mrdChoice);
		mrbWeiXin = (RadioButton) findViewById(R.id.mrbWeiXin);
		mrbPay = (RadioButton) findViewById(R.id.mrbPay);
		metMessage = (EditText) findViewById(R.id.metMessage);
		mPay = (Button) findViewById(R.id.mPay);
		mtvPrice = (TextView) findViewById(R.id.mtvPrice);
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mTvTitle.setText("支付确认");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mPay.setOnClickListener(this);
	}

	@Override
	public void initData() {
		shopModel = (HouseModel) this.getIntent().getSerializableExtra("msg");

		userModel = (UserModel) MemberUserUtils.getBean(this, "user_messgae");

		mtvPrice.setText(shopModel.getHouseMoney()+"元/月");
		mtvName.setText(shopModel.getHouseName());
		
		mrdChoice.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == mrbWeiXin.getId()) {
					choiceType = 1;
				} else if (checkedId == mrbPay.getId()) {
					choiceType = 2;
				}
			}
		});
	}

	/**
	 * 订单的添加
	 * 
	 * @param isShow
	 */
	private void OrderAction(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "addOrder");
		params.put("applyHouseId", shopModel.getHouseId());
		params.put("applyHouseName", shopModel.getHouseName());
		params.put("applyHouseMoney", shopModel.getHouseMoney());
		params.put("applyUserId", MemberUserUtils.getUid(this));
		params.put("applyUserName",MemberUserUtils.getName(this));
		params.put("applyHouseUserId", shopModel.getUserId()+"");
		httpPost(Consts.URL + Consts.APP.OrderAction, params, Consts.actionId.resultFlag, isShow, "正在支付...");
	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);
		ToastUtil.show(PayMessageActivity.this, entry.getRepMsg() + "，可在我的预约查看");
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				PayMessageActivity.this.finish();
			}
		}, 2000);
	}

	@Override
	protected void callBackAllFailure(String strMsg, int actionId) {
		super.callBackAllFailure(strMsg, actionId);
		ToastUtil.show(PayMessageActivity.this, strMsg);
	}

}
