package com.rental;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.reflect.TypeToken;
import com.wang.adapter.PractitionersAdapter;
import com.wang.base.BaseActivity;
import com.wang.config.Consts;
import com.wang.db.MemberUserUtils;
import com.wang.model.CategoryModel;
import com.wang.model.ResponseEntry;
import com.wang.model.UserModel;
import com.wang.util.ToastUtil;
import com.wang.view.DialogListMsg;

/**
 * 
 * @author WangKui 用户信息的展示
 */
public class UserActivity extends BaseActivity {

	// 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;
	private TextView mName;
	private String state;
	private RelativeLayout mtvUserName;
	private TextView mPhone;
	private TextView mAddress;
	private RelativeLayout mrlPhone;
	private List<CategoryModel> list_result = new ArrayList<CategoryModel>();
	private DialogListMsg dialogListMsg;
	private ImageView mtvIsVip;
	private RelativeLayout mtvApply;
	private int ctposNumber = 0;
	private TextView mtttYuyue;
	private RelativeLayout mrlAddress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		initWidget();
		initData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mIvBack:
			UserActivity.this.finish();
			break;
		case R.id.mtvApply:
			dialogListMsg.Show();
			break;
		case R.id.mrlAddress:
			Intent mrlAddress = new Intent(UserActivity.this, UpdatePswdActivity.class);
			startActivity(mrlAddress);
			break;
		}
	}

	@Override
	public void initWidget() {
		mrlAddress = (RelativeLayout) findViewById(R.id.mrlAddress);
		mtvApply = (RelativeLayout) findViewById(R.id.mtvApply);
		mtttYuyue = (TextView) findViewById(R.id.mtttYuyue);
		mtvIsVip = (ImageView) findViewById(R.id.mtvIsVip);
		mPhone = (TextView) findViewById(R.id.mPhone);
		mAddress = (TextView) findViewById(R.id.mAddress);
		mrlPhone = (RelativeLayout) findViewById(R.id.mrlPhone);
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mName = (TextView) findViewById(R.id.mName);

		mTvTitle.setText("My profile info");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mtvApply.setOnClickListener(this);
		mrlAddress.setOnClickListener(this);
	}

	@Override
	public void initData() {

		try {
			
			MessageAction(false);
			dialogListMsg = new DialogListMsg(this);
			dialogListMsg.setTitle().setText("请选择预约户型");
			UserModel userModel = (UserModel) MemberUserUtils.getBean(this, "user_messgae");
			mName.setText("username： " + MemberUserUtils.getName(this));
			mPhone.setText("phone number： " + userModel.getUphone());

			if (userModel.getIsVip().equals("1")) {
				mtvIsVip.setVisibility(View.VISIBLE);
			} else {
				mtvIsVip.setVisibility(View.GONE);
			}
			
			
			if (userModel.getUtype().equals("1")) {
				mtvApply.setVisibility(View.GONE);
			}
			

			if (TextUtils.isEmpty(userModel.getApplyTypeId())) {
				mtttYuyue.setText("我的预约房源户型：暂未预约");
			} else {
				mtttYuyue.setText("我的预约房源户型："+userModel.getApplyTypeName());
			}
			
			dialogListMsg.show_listview().setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					ctposNumber = position;
					mtttYuyue.setText("我的预约房源户型："+list_result.get(position).getTypeName());
					dialogListMsg.Close();
					
					RegisterAction(false);

				}
			});

			dialogListMsg.submit_no().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dialogListMsg.Close();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private void RegisterAction(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "updateApplyTypeId");
		params.put("applyTypeId", list_result.get(ctposNumber).getTypeId()+"");
		params.put("applyTypeName", list_result.get(ctposNumber).getTypeName()+"");
		params.put("uid", MemberUserUtils.getUid(this));
		httpPost(Consts.URL + Consts.APP.RegisterAction, params, Consts.actionId.resultCode, isShow, "正在更新...");
	}

	private void MessageAction(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "listTypePhoneMessage");
		httpPost(Consts.URL + Consts.APP.ShopAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);
		switch (actionId) {
		case Consts.actionId.resultFlag:
			if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {

				String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);
				if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
					list_result = mGson.fromJson(entry.getData(), new TypeToken<List<CategoryModel>>() {
					}.getType());
					PractitionersAdapter practitionersAdapter = new PractitionersAdapter(UserActivity.this);
					practitionersAdapter.setData(list_result);
					dialogListMsg.show_listview().setAdapter(practitionersAdapter);
				}
			}
			break;
		case Consts.actionId.resultCode:
			ToastUtil.show(UserActivity.this, entry.getRepMsg());
			break;
		}
	}
}
