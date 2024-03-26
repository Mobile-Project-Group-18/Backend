package com.wang.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wang.model.TypeModel;
import com.rental.R;

public class TypeAdapter extends BaseAdapter {
	private Context mContext;
	private List<TypeModel> list_result;

	public TypeAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public TypeAdapter(Context mContext, List<TypeModel> list_msg) {
		super();
		this.mContext = mContext;
		this.list_result = list_msg;
		notifyDataSetChanged();
	}

	public List<TypeModel> setData(List<TypeModel> list_str) {
		return list_result = list_str;

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
		ViewHolder vh = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.add_item, null);
			vh = new ViewHolder();
			vh.zoom_msg = (TextView) convertView.findViewById(R.id.zoom_msg);

			convertView.setTag(vh);

		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		vh.zoom_msg.setText(list_result.get(position).getTypeName());
		return convertView;
	}

	class ViewHolder {
		TextView zoom_msg;
	}
}