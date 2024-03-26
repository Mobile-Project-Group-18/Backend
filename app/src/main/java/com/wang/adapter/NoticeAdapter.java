package com.wang.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wang.model.NoticeModel;
import com.rental.R;

public class NoticeAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<NoticeModel> list_result;
	private int posIndex;
	private Context mContext;

	public NoticeAdapter(Context context, List<NoticeModel> list_result) {
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
			convertView = inflater.inflate(R.layout.item_message_new, null);
			holder = new ViewHolder();
			holder.newsShowTime = (TextView) convertView.findViewById(R.id.newsShowTime);
			holder.newsShowTitle = (TextView) convertView.findViewById(R.id.newsShowTitle);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.newsShowTime.setText("Publish time：" + list_result.get(position).getNewsTime());
		holder.newsShowTitle.setText(list_result.get(position).getNewsTitle());

		return convertView;

	}

	private class ViewHolder {
		private TextView newsShowTitle;
		private TextView newsShowTime;
	}

	public void setPos(int pos) {
		posIndex = pos;
	}

}
