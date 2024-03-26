package com.wang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.wang.base.BaseFragment;
import com.wang.db.MemberUserUtils;
import com.wang.model.UserModel;
import com.rental.LoginActivity;
import com.rental.MyApplyActivity;
import com.rental.MyZuKeActivity;
import com.rental.R;
import com.rental.SoftMessageActivity;
import com.rental.UserActivity;

/**
 * 
 * @author WangKui
 * 
 */
public class MeFragment extends BaseFragment {

	// 获取view
	private View rootView;
	private RelativeLayout mtvUser;
	private RelativeLayout mrlFabu;
	private RelativeLayout mtvSoft;
	private RelativeLayout mrlmyorder;
	private Button mExit;
	
	
	
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_content, null);

		return rootView;
	}

	/**
	 * 获取控件
	 */
	@Override
	public void initWidget() {
		mrlmyorder = (RelativeLayout) rootView.findViewById(R.id.mrlmyorder);
		mtvSoft = (RelativeLayout) rootView.findViewById(R.id.mtvSoft);
		mrlFabu = (RelativeLayout) rootView.findViewById(R.id.mrlFabu);
		mtvUser = (RelativeLayout) rootView.findViewById(R.id.mtvUser);
		mrlFabu.setOnClickListener(this);
		mtvUser.setOnClickListener(this);
		mtvSoft.setOnClickListener(this);
		mrlmyorder.setOnClickListener(this);

		mExit = (Button) rootView.findViewById(R.id.mExit);
		mExit.setOnClickListener(this);
	}

	/**
	 * 处理点击事件
	 */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		
		case R.id.mrlFabu:
			Intent mrlFabu = new Intent(getActivity(), MyZuKeActivity.class);
			getActivity().startActivity(mrlFabu);
			break;
		case R.id.mrlmyorder:
			Intent mrlCollect = new Intent(getActivity(), MyApplyActivity.class);
			getActivity().startActivity(mrlCollect);
			break;

		case R.id.mtvSoft:
			Intent mtvSoft = new Intent(getActivity(), SoftMessageActivity.class);
			getActivity().startActivity(mtvSoft);
			break;

		case R.id.mtvUser:
			Intent mtvUser = new Intent(getActivity(), UserActivity.class);
			getActivity().startActivity(mtvUser);
			break;
			

		case R.id.mExit:
			MemberUserUtils.setUid(getActivity(),"");
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			break;

		default:
			break;
		}

	}

	/**
	 * 处理数据
	 */
	@Override
	public void initData() {

		UserModel userModel = (UserModel) MemberUserUtils.getBean(getActivity(), "user_messgae");

		if (userModel.getUtype().equals("1")) {
			mrlmyorder.setVisibility(View.GONE);
		} else {
			mrlFabu.setVisibility(View.GONE);
		}
		
	}

	@Override
	public void onResume() {
		super.onResume();
		initWidget();
		initData();
	}
}
