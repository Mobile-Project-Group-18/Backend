package com.rental;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.db.MemberUserUtils;
import com.wang.fragment.HobbyMainFragment;
import com.wang.fragment.MeFragment;
import com.wang.model.UserModel;

public class FrameworkActivity extends FragmentActivity implements OnClickListener {

	/**
	 * 用于展示消息的Fragment
	 */
	private HobbyMainFragment messageFragment;

	/**
	 * 用于展示联系人的Fragment
	 */
	private MeFragment imFragment;
//
//	/**
//	 * 用于展示动态的Fragment
//	 */
//	private MeFragment newsFragment;

	/**
	 * 消息界面布局
	 */
	private View messageLayout;

	/**
	 * 设置界面布局
	 */
	private View settingLayout;
//
	/**
	 * 设置界面布局
	 */
//	private View person_layout;

	/**
	 * 在Tab布局上显示消息图标的控件
	 */
	private ImageView messageImage;

	/**
	 * 在Tab布局上显示设置图标的控件
	 */
	private ImageView settingImage;

	/**
	 * 在Tab布局上显示设置图标的控件
	 */
//	private ImageView person_image;

	/**
	 * 在Tab布局上显示消息标题的控件
	 */
	private TextView messageText;

	/**
	 * 在Tab布局上显示设置标题的控件
	 */
	private TextView settingText;
//
	/**
	 * 在Tab布局上显示设置标题的控件
	 */
//	private TextView person_text;

	private long mExitTime;
	private static final int INTERVAL = 2000;
	Intent intent;

	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager;

	private Button mivCreateMessage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_framework);


		setWindowStatus();
		// 初始化布局元素
		initViews();
		findViewById(R.id.iv_menu).setOnClickListener(clickListener);
		// getSupportFragmentManager().beginTransaction()
		fragmentManager = getSupportFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
	}

	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_menu:// 打开左边抽屉

				SharedPreferences read = getSharedPreferences("lock", MODE_WORLD_READABLE);
				// 步骤2：获取文件中的值
				String value = read.getString("user_id", "");
				if (TextUtils.isEmpty(value)) {
					Intent intent = new Intent(FrameworkActivity.this, LoginActivity.class);
					startActivity(intent);
				} else {
					// Intent intent = new Intent(MainActivity.this,
					// UserActivity.class);
					// startActivity(intent);
				}
				break;
			}
		}
	};

	/**
	 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	 */
	private void initViews() {
		
		mivCreateMessage = (Button) findViewById(R.id.mivCreateMessage);
		messageLayout = findViewById(R.id.message_layout);
		settingLayout = findViewById(R.id.setting_layout);
//		person_layout = findViewById(R.id.person_layout);

		messageImage = (ImageView) findViewById(R.id.message_image);
		settingImage = (ImageView) findViewById(R.id.setting_image);
//		person_image = (ImageView) findViewById(R.id.person_image);

		messageText = (TextView) findViewById(R.id.message_text);
		settingText = (TextView) findViewById(R.id.setting_text);
//		person_text = (TextView) findViewById(R.id.person_text);

		messageLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
//		person_layout.setOnClickListener(this);
		mivCreateMessage.setOnClickListener(this);
		
		UserModel userModel = (UserModel) MemberUserUtils.getBean(this, "user_messgae");
		
		if(userModel.getUtype().equals("1")){
			mivCreateMessage.setVisibility(View.VISIBLE);
		}else{
			mivCreateMessage.setVisibility(View.GONE);
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.message_layout:
			// 当点击了消息tab时，选中第1个tab
			setTabSelection(0);
			break;
		case R.id.setting_layout:
			// 当点击了设置tab时，选中第4个tab
			if (MemberUserUtils.getUid(this).equals("")) {
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} else {
				setTabSelection(1);
			}
		
			break;
		case R.id.person_layout:
			// 当点击了设置tab时，选中第4个tab
			setTabSelection(2);
			break;
		case R.id.mivCreateMessage:
			if (MemberUserUtils.getUid(this).equals("")) {
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			} else {
				Intent intent = new Intent(FrameworkActivity.this, CreatActivity.class);
				startActivity(intent);
			}
			
			
	
			break;
				
			
		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
	 */
	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			messageImage.setImageResource(R.drawable.scenic_true);
			messageText.setTextColor(Color.parseColor("#72c557"));
			if (messageFragment == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				messageFragment = new HobbyMainFragment();
				transaction.add(R.id.content, messageFragment);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(messageFragment);
			}
			break;
		case 1:
			// 当点击了联系人tab时，改变控件的图片和文字颜色
			settingImage.setImageResource(R.drawable.me_true);
			settingText.setTextColor(Color.parseColor("#72c557"));
			if (imFragment == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				imFragment = new MeFragment();
				transaction.add(R.id.content, imFragment);
			} else {
				// 如果ContactsFragment不为空，则直接将它显示出来
				transaction.show(imFragment);
			}
			break;
//		case 2:
//			// 当点击了动态tab时，改变控件的图片和文字颜色
//			person_image.setImageResource(R.drawable.travels_true);
//			person_text.setTextColor(Color.parseColor("#72c557"));
//			if (newsFragment == null) {
//				// 如果NewsFragment为空，则创建一个并添加到界面上
//				newsFragment = new MeFragment();
//				transaction.add(R.id.content, newsFragment);
//			} else {
//				// 如果NewsFragment不为空，则直接将它显示出来
//				transaction.show(newsFragment);
//			}
//			break;
		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		messageImage.setImageResource(R.drawable.scenic_false);
		messageText.setTextColor(Color.parseColor("#82858b"));
		settingImage.setImageResource(R.drawable.me_false);
		settingText.setTextColor(Color.parseColor("#82858b"));
//		person_image.setImageResource(R.drawable.travels_false);
//		person_text.setTextColor(Color.parseColor("#82858b"));

	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (messageFragment != null) {
			transaction.hide(messageFragment);
		}
		if (imFragment != null) {
			transaction.hide(imFragment);
		}
//		if (newsFragment != null) {
//			transaction.hide(newsFragment);
//		}

	}

	// 设置状态栏
	private void setWindowStatus() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			// 设置状态栏颜色
			getWindow().setBackgroundDrawableResource(R.color.main_color);
		}
	}

	public void onBackPressed() {
		if (System.currentTimeMillis() - mExitTime > INTERVAL) {
			Toast.makeText(this, "tap again to quit the app", Toast.LENGTH_SHORT).show();
			mExitTime = System.currentTimeMillis();
		} else {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			this.startActivity(intent);
			System.exit(0);
		}
	}


}