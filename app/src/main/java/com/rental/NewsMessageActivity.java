package com.rental;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.base.BaseActivity;
import com.wang.model.NoticeModel;

public class NewsMessageActivity extends BaseActivity {
	// title
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;
	// 查询按钮
	private TextView mtvtitle;
	private TextView mtvcontent;
	private TextView newsTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newsmsg);
		initWidget();
		initData();
	}

	@Override
	public void initWidget() {
		newsTime = (TextView) findViewById(R.id.newsTime);
		mtvtitle = (TextView) findViewById(R.id.mtvtitle);
		mtvcontent = (TextView) findViewById(R.id.mtvcontent);
		
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mTvTitle.setText("政策详情");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.mIvBack:
			NewsMessageActivity.this.finish();
			break;
		}
	}

	@Override
	public void initData() {
		
		
		NoticeModel	noticeModel =(NoticeModel) this.getIntent().getSerializableExtra("msg");
		mtvtitle.setText(noticeModel.getNewsTitle());
		mtvcontent.setText(noticeModel.getNewsContent());
		newsTime.setText("发布时间："+noticeModel.getNewsTime());
	}

	



}
