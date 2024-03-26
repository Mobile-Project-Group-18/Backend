package com.wang.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.config.Consts;
import com.wang.model.ApplyModel;
import com.rental.R;
import com.squareup.picasso.Picasso;

public class OrderListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<ApplyModel> list_result;
	private int posIndex;
	private Context mContext;

	public OrderListAdapter(Context context, List<ApplyModel> list_result) {
		mContext = context;
		inflater = LayoutInflater.from(context);
		this.list_result = list_result;
	}

	@Override
	public int getCount() {
		return list_result.size();
	}

	@Override
	public Object getItem(int position) {
		return list_result.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.look_item, null);
			holder = new ViewHolder();
			holder.mTvTitle = (TextView) convertView.findViewById(R.id.mTvTitle);
			holder.mTvMoney = (TextView) convertView.findViewById(R.id.mTvMoney);
			holder.mivShop = (ImageView) convertView.findViewById(R.id.mivShop);
			holder.mtvPhone = (TextView) convertView.findViewById(R.id.mtvPhone);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// try {

		if (list_result.get(position).getApplyState().equals("1")) {
			holder.mTvTitle.setText(list_result.get(position).getApplyHouseName() + "(待处理)");
		} else if (list_result.get(position).getApplyState().equals("2")) {
			holder.mTvTitle.setText(list_result.get(position).getApplyHouseName() + "(已同意)");
		} else {
			holder.mTvTitle.setText(list_result.get(position).getApplyHouseName() + "(已拒绝)");
		}

		holder.mTvMoney.setText("预约价位：" + list_result.get(position).getApplyHouseMoney() + "元/月");
		holder.mtvPhone.setText("预约时间：" + list_result.get(position).getApplyTime());
		if (!TextUtils.isEmpty(list_result.get(position).getHouseMessage().getHouseImage())) {
			Picasso.with(mContext).load(Consts.URL_IMAGE_LOCAL + list_result.get(position).getHouseMessage().getHouseImage())
					.placeholder(R.drawable.default_drawable_show_pictrue).into(holder.mivShop);
		}

		return convertView;

	}

	private class ViewHolder {
		private TextView mTvTitle;
		private TextView mTvMoney;
		private TextView mtvPhone;
		private TextView mtvTime;
		private TextView mtvState;
		private ImageView mivShop;
	}

	public void setPos(int pos) {
		posIndex = pos;
	}

}
