package com.wang.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import net.tsz.afinal.http.AjaxParams;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.wang.adapter.LookListAdapter;
import com.wang.base.BaseFragment;
import com.wang.config.Consts;
import com.wang.db.MemberUserUtils;
import com.wang.model.HouseModel;
import com.wang.model.ResponseEntry;
import com.wang.model.UserModel;
import com.wang.observable.HouseObservable;
import com.wang.util.ToastUtil;
import com.rental.HouseMessageActivity;
import com.rental.R;

/**
 * 精选
 * 
 * @author ansen
 * @create time 2016-04-19
 */
public class MainFragment extends BaseFragment implements Observer {

	// 获取view
	private View rootView;

	// 获取控件
	private ListView mListMessage;
	View convertView;
	private LinearLayout mllNomessage;
	private List<HouseModel> list_result = new ArrayList<HouseModel>();
	private List<HouseModel> list_result_search = new ArrayList<HouseModel>();
	private List<HouseModel> list_result_update = new ArrayList<HouseModel>();
	// NoticeAdapter noticeAdapter;
	private TextView mtvTipMessage;

	private EditText metName;
	private TextView mtvSearch;

	private int choiceFlag = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_selected, null);
		initWidget();
		initData();
		return rootView;
	}

	@Override
	public void initWidget() {

		metName = (EditText) rootView.findViewById(R.id.metName);
		mtvSearch = (TextView) rootView.findViewById(R.id.mtvSearch);
		mtvSearch.setOnClickListener(this);

		mllNomessage = (LinearLayout) rootView.findViewById(R.id.mllNomessage);
		mListMessage = (ListView) rootView.findViewById(R.id.mListMessage);
		mtvTipMessage = (TextView) rootView.findViewById(R.id.mtvTipMessage);
		
		metName.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			
				if(s.length()==0){
					listMessage(false);
				}
			}
		});
	}
	/*
	 * 隐藏软键盘
	 */
	public void hideSoft(Context context, EditText edittext) {
		try {
			if (edittext != null && context != null) {
				InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onClick(View v) {
		if (TextUtils.isEmpty(metName.getText().toString())) {
			ToastUtil.ShowCentre(getActivity(), "请输入好友ID");
			return;
		}
		listSearchMsg(true, metName.getText().toString());
		hideSoft(getActivity(), metName);
	}
	
	private void listSearchMsg(boolean isShow, String searchmessage) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "queryMessage");
		params.put("searchmessage",searchmessage);
		httpPost(Consts.URL + Consts.APP.HouseAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
	}
	

	private void listMessage(boolean isShow) {
		AjaxParams params = new AjaxParams();

		UserModel userModel = (UserModel) MemberUserUtils.getBean(getActivity(), "user_messgae");

		if (TextUtils.isEmpty(userModel.getApplyTypeId())) {
			params.put("action_flag", "listShopMessage");
		} else {
			params.put("action_flag", "listShopVipMessage");
			params.put("houseCategory", userModel.getApplyTypeId());
		}

		httpPost(Consts.URL + Consts.APP.ShopAction, params, Consts.actionId.resultCode, isShow, "正在加载...");
	}

	@Override
	public void initData() {
		listMessage(true);
		//
		mListMessage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

				
				if(choiceFlag==0){
					Intent intent = new Intent(getActivity(), HouseMessageActivity.class);
					intent.putExtra("msg", list_result.get(pos));
					getActivity().startActivity(intent);
				}else{
					Intent intent = new Intent(getActivity(), HouseMessageActivity.class);
					intent.putExtra("msg", list_result_search.get(pos));
					getActivity().startActivity(intent);
				}
				
			}
		});

	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);

		switch (actionId) {
		case Consts.actionId.resultCode:
			choiceFlag =0;
			if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {
				String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);

				if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
					list_result.clear();
					list_result = mGson.fromJson(entry.getData(), new TypeToken<List<HouseModel>>() {
					}.getType());
					LookListAdapter noticeAdapter = new LookListAdapter(getActivity(), list_result);
					mListMessage.setAdapter(noticeAdapter);
				} else {
					mllNomessage.setVisibility(View.VISIBLE);
				}
			}
			break;
		case Consts.actionId.resultFlag:
			choiceFlag =1;
			if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {
				String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);

				if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
					list_result_search.clear();
					list_result_search = mGson.fromJson(entry.getData(), new TypeToken<List<HouseModel>>() {
					}.getType());
					LookListAdapter noticeAdapter = new LookListAdapter(getActivity(), list_result_search);
					mListMessage.setAdapter(noticeAdapter);
				} else {
					mllNomessage.setVisibility(View.VISIBLE);
				}
			}
			break;
		}

	}

	@Override
	protected void callBackAllFailure(String strMsg, int actionId) {
		super.callBackAllFailure(strMsg, actionId);
		ToastUtil.show(getActivity(), strMsg);

	}

	@Override
	public void onResume() {
		super.onResume();
		HouseObservable.getInstance().addObserver(MainFragment.this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		HouseObservable.getInstance().deleteObserver(MainFragment.this);

	}

	@Override
	public void update(Observable observable, Object data) {
		list_result_update.clear();
		HouseModel intersetModel = (HouseModel) data;
		list_result_update.add(intersetModel);
		list_result_update.addAll(list_result);
		LookListAdapter noticeAdapter = new LookListAdapter(getActivity(), list_result_update);
		mListMessage.setAdapter(noticeAdapter);

	}

}
