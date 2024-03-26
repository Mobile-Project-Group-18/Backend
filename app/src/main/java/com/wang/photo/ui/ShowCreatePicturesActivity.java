package com.wang.photo.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.model.SelectImageItem;
import com.wang.photo.HackyViewPager;
import com.wang.photo.IconPageIndicator;
import com.wang.photo.IconPagerAdapter;
import com.wang.photo.PictureSlideDeleteFragment;
import com.wang.util.SystemBarTintManager;
import com.rental.R;

public class ShowCreatePicturesActivity extends FragmentActivity implements OnClickListener {

	private static final String ISLOCKED_ARG = "isLocked";
	private ArrayList<SelectImageItem> imgUrls;
	private int mIndex = 0;
	private ViewPager mViewPager;

	// title
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;
	// 介绍
	private TextView mIvStu;
	
	private String flagMyShop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_create_pictures);
		
		if (savedInstanceState != null) {
			boolean isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG, false);
			((HackyViewPager) mViewPager).setLocked(isLocked);
		}
		initWidget();
		initData();
		
		initTiltBar();
	}



	IconPageIndicator icon;

	int selectPostion = 0;

	private void initViewPage() {
		PictureSlidePagerAdapter mPagerAdapter = new PictureSlidePagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		icon.setViewPager(mViewPager, selectPostion);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				selectPostion = arg0;
				initPostion(selectPostion);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		initPostion(selectPostion);

	}

	private class PictureSlidePagerAdapter extends FragmentStatePagerAdapter implements IconPagerAdapter {

		public PictureSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int index) {
			return newInstance(index);
		}

		@Override
		public int getCount() {
			return imgUrls.size();
		}

		@Override
		public int getIconResId(int index) {
			return R.drawable.square_indicator_selector;
		}

		@Override
		public int getRealCount() {
			return imgUrls.size();
		}
	}

	public PictureSlideDeleteFragment newInstance(int index) {
		PictureSlideDeleteFragment f = new PictureSlideDeleteFragment();
		Bundle args = new Bundle();
		args.putInt("index", index);
		// args.putString("url", "file://" + imgUrls.get(index).getUrl());
		args.putString("url", imgUrls.get(index).getUrl());
		args.putInt("size", imgUrls.size());
		f.setArguments(args);
		return f;
	}

	private boolean isViewPagerActive() {
		return (mViewPager != null && mViewPager instanceof HackyViewPager);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (isViewPagerActive()) {
			outState.putBoolean(ISLOCKED_ARG, ((HackyViewPager) mViewPager).isLocked());
		}
		super.onSaveInstanceState(outState);
	}

	private void deleteImgByPostion(int postion) {
		try {
			if (imgUrls.size() == 1 && postion == 0) {
				imgUrls.remove(0);
				// 删除完了
				// 只有一张且删除的是第一张
				goBack();
			} else if (postion == imgUrls.size() - 1) {
				imgUrls.remove(postion);
				selectPostion = imgUrls.size() - 1;
				initViewPage();
			} else if (postion == 0) {
				imgUrls.remove(postion);
				selectPostion = 0;
				initViewPage();
			} else {
				imgUrls.remove(postion);
				initViewPage();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean dispatchKeyEvent(KeyEvent event) {

		if (KeyEvent.ACTION_DOWN == event.getAction() && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			goBack();
			return true;
		}
		/** 按下其它键，调用父类方法，进行默认操作 */
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mIvBack:
			goBack();
			break;
		case R.id.mIvStu:
			deleteImgByPostion(selectPostion);
			break;

		default:
			break;
		}
	}

	public void initWidget() {
		icon = (IconPageIndicator) findViewById(R.id.indicator);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mIvStu = (TextView) findViewById(R.id.mIvStu);
		mIvStu.setText("删除");
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mTvTitle.setText("显示照片");
		mIvBack.setVisibility(View.VISIBLE);
		mIvStu.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mIvStu.setOnClickListener(this);
	}

	public void initData() {
		imgUrls = (ArrayList<SelectImageItem>) getIntent().getSerializableExtra("piction_path");
		flagMyShop = this.getIntent().getStringExtra("flagMyShop");
		
		if(flagMyShop.equals("myShop")){
			mIvStu.setVisibility(View.GONE);
		}
		
		
		Log.i("pony_log", imgUrls.size()+"");
		
		
		mIndex = getIntent().getIntExtra("position", 0);
		selectPostion = mIndex;
		if (imgUrls != null && imgUrls.size() > 0) {
//			imgUrls.remove(imgUrls.size() - 1);
			initViewPage();
		}

	}

	private void goBack() {
		SelectImageItem addItem = new SelectImageItem();
		addItem.setSid(100);// 添加的图标
		imgUrls.add(addItem);

		Intent intent = new Intent();
		intent.putExtra("data", imgUrls);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
	
	private void initPostion(int postion) {
		mTvTitle.setText((postion + 1) + "/" + imgUrls.size());
	}

	
	 /**
     * titlebar变颜色
     */
    public void initTiltBar() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setTranslucentStatus(true);
            }
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            // 使用颜色资源
            tintManager.setStatusBarTintResource(R.color.main_color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
