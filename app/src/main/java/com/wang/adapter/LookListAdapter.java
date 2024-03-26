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
import com.wang.model.HouseModel;
import com.rental.R;
import com.squareup.picasso.Picasso;

public class LookListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<HouseModel> list_result;
	private int posIndex;
	private Context mContext;

	public LookListAdapter(Context context, List<HouseModel> list_result) {
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
//		try {
			holder.mTvTitle.setText(list_result.get(position).getHouseName());
			holder.mTvMoney.setText("Price："+list_result.get(position).getHouseMoney()+" EUR/Month");
			holder.mtvPhone.setText("Layout："+list_result.get(position).getTypeName());
			if(!TextUtils.isEmpty(list_result.get(position).getHouseImage())){
				Picasso.with(mContext).load(Consts.URL_IMAGE_LOCAL+list_result.get(position).getHouseImage().split(",")[0]).placeholder(R.drawable.default_drawable_show_pictrue)
				.into(holder.mivShop);
			}
			
//			if (TextUtils.isEmpty(list_result.get(position).getOstate())) {
//				holder.mtvState.setText("可申请");
//			}else if(list_result.get(position).getOstate().equals("0")) {
//				ToastUtil.ShowCentre(getActivity(), "此工作已申请,请等待招聘者处理");	
//			} else {
//				holder.mtvState.setText("已申请");
//			}
//			
			
			
//			if(list_result.get(position).getOstate().equals("1")){
//				holder.mtvState.setText("已同意");
//				holder.mtvState.setTextColor(Color.parseColor("#40ac44"));
//			}else if(list_result.get(position).getOstate().equals("0")) {
//				holder.mtvState.setText("待处理");
//				holder.mtvState.setTextColor(Color.parseColor("#ff0000"));
//			}else{
//				holder.mtvState.setText("可申请");
//				holder.mtvState.setTextColor(Color.parseColor("#666666"));
//			}
//			

//		} catch (Exception e) {
//			// TODO: handle exception
//		}

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
