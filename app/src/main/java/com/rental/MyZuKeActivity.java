package com.rental;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.wang.adapter.OrderListAdapter;
import com.wang.base.BaseActivity;
import com.wang.config.Consts;
import com.wang.db.MemberUserUtils;
import com.wang.model.ApplyModel;
import com.wang.model.ResponseEntry;
import com.wang.util.ToastUtil;

/**
 * 类别
 * 
 * @author WangKui
 * 
 *         2016-12-26
 */
public class MyZuKeActivity extends BaseActivity {

	// 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;
	private TextView mIvStu;
	private ListView mListMessage;
	private List<ApplyModel> list_result = new ArrayList<ApplyModel>();
	private String state;
	private LinearLayout mllNomessage;
	private Button mExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_im);
		initWidget();
		initData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mIvBack:
			finish();
			break;



		}
	}

	@Override
	public void initWidget() {
		mIvStu = (TextView) findViewById(R.id.mIvStu);
		mIvStu.setText("add");
		mIvStu.setVisibility(View.GONE);
		mllNomessage = (LinearLayout) findViewById(R.id.mllNomessage);
		mListMessage = (ListView) findViewById(R.id.mListMessage);

		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		state = this.getIntent().getStringExtra("state");
		mTvTitle.setText("Tenants' appointmentssss");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mIvStu.setOnClickListener(this);


	}

	@Override
	public void initData() {
		MessageAction(true);
		mListMessage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

				Intent intent = new Intent(MyZuKeActivity.this, OrderMessageActivity.class);
				intent.putExtra("msg", list_result.get(pos));
				MyZuKeActivity.this.startActivity(intent);
			}
		});
	}


	private void MessageAction(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "listMyUserOrderZeKe");
		params.put("applyHouseUserId", MemberUserUtils.getUid(this));
		httpPost(Consts.URL + Consts.APP.OrderAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);

		switch (actionId) {
		case Consts.actionId.resultFlag:
			if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {

				String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);
				if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
					list_result = mGson.fromJson(entry.getData(), new TypeToken<List<ApplyModel>>() {
					}.getType());

					OrderListAdapter lookListAdapter = new OrderListAdapter(MyZuKeActivity.this, list_result);
					mListMessage.setAdapter(lookListAdapter);
				}else{
					mllNomessage.setVisibility(View.VISIBLE);
				}
			}
			break;
		default:
			break;
		}

	}

	@Override
	protected void callBackAllFailure(String strMsg, int actionId) {
		super.callBackAllFailure(strMsg, actionId);
		ToastUtil.show(MyZuKeActivity.this, strMsg);

	}

	@Override
	protected void onResume() {
		super.onResume();
	
	}
}
