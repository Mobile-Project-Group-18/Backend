package com.wang.fragment;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.wang.adapter.NoticeAdapter;
import com.wang.base.BaseFragment;
import com.wang.config.Consts;
import com.wang.model.NoticeModel;
import com.wang.model.ResponseEntry;
import com.wang.util.ToastUtil;
import com.rental.NewsMessageActivity;
import com.rental.R;

/**
 * 精选
 * 
 * @author ansen
 * @create time 2016-04-19
 */
public class NewsFragment extends BaseFragment {
	// 获取view
	private View rootView;
	// 获取控件
	private ListView mListMessage;
	View convertView;
	private LinearLayout mllNomessage;
	private List<NoticeModel> list_result = new ArrayList<NoticeModel>();
	private TextView mtvTipMessage;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news, null);
		initWidget();
		initData();
		return rootView;
	}

	@Override
	public void initWidget() {
		
		mllNomessage = (LinearLayout) rootView.findViewById(R.id.mllNomessage);
		mListMessage = (ListView) rootView.findViewById(R.id.mListMessage);
		mtvTipMessage = (TextView) rootView.findViewById(R.id.mtvTipMessage);

	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void initData() {
		listPhoneMessage(true);
		mListMessage.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

				Intent intent = new Intent(getActivity(), NewsMessageActivity.class);
				intent.putExtra("msg", list_result.get(pos));
				getActivity().startActivity(intent);
			}
		});
	}

	private void listPhoneMessage(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "listPhoneMessage");
		httpPost(Consts.URL + Consts.APP.NewsAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);

		switch (actionId) {
		case Consts.actionId.resultFlag:
			if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {
				String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);

				if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
					list_result.clear();
					list_result = mGson.fromJson(entry.getData(), new TypeToken<List<NoticeModel>>() {
					}.getType());
					NoticeAdapter noticeAdapter = new NoticeAdapter(getActivity(), list_result);
					mListMessage.setAdapter(noticeAdapter);
				} else {
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
		ToastUtil.show(getActivity(), strMsg);

	}

}
