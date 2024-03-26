package com.wang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.wang.adapter.InstructorAdapter;
import com.wang.adapter.SelectedImageAdapter;
import com.wang.model.Instructor;
import com.wang.model.SelectImageItem;

public class GridLayout extends LinearLayout {

	private InstructorAdapter adapter;
	private SelectedImageAdapter selectAdapter;
	private InstructorItemClickListner instructorItemClickListner;
	private Context context;

	public GridLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void setAdapter(InstructorAdapter adapter,final InstructorItemClickListner instructorItemClickListner) {
		try {
			this.adapter = adapter;
			this.instructorItemClickListner = instructorItemClickListner;
			this.removeAllViews();
			for (int i = 0; i < adapter.getCount(); i++) {
				final int postion = i;
				final Instructor map = adapter.getItem(i);
				View view = adapter.getView(i, null, null);
//				view.setPadding(0, 0, 10, 0);
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						instructorItemClickListner.instructorItemClick(postion, map);
					}
				});
				this.setOrientation(HORIZONTAL);
				this.addView(view, new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setGridAdapter(SelectedImageAdapter adapter,
			final ImageItemClickListner clickListner) {
		try {
			this.selectAdapter = adapter;
			this.removeAllViews();
			for (int i = 0; i < selectAdapter.getCount(); i++) {
				if (i < 9) {

					final int itemPos = i;
					final SelectImageItem item = selectAdapter.getItem(i);
					View view = selectAdapter.getView(i, null, null);
					view.setPadding(0, 0, 10, 0);
					view.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							clickListner.imageItemClick(itemPos, item);
						}
					});
					this.setOrientation(HORIZONTAL);
					this.addView(view, new LinearLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
