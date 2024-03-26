package com.rental;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wang.adapter.PractitionersAdapter;
import com.wang.adapter.SelectedImageAdapter;
import com.wang.base.BaseActivity;
import com.wang.config.Consts;
import com.wang.db.MemberUserUtils;
import com.wang.model.CategoryModel;
import com.wang.model.HouseModel;
import com.wang.model.ResponseEntry;
import com.wang.model.SelectImageItem;
import com.wang.observable.HouseObservable;
import com.wang.photo.ui.SelectImagesActivity;
import com.wang.photo.ui.ShowCreatePicturesActivity;
import com.wang.util.LoadingDialog;
import com.wang.util.ToastUtil;
import com.wang.util.UploadUtils;
import com.wang.view.DialogListMsg;
import com.wang.view.GridLayout;
import com.wang.view.ImageItemClickListner;

public class CreatActivity extends BaseActivity implements ImageItemClickListner {
	// 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;
	GridLayout grid_instructor;
	HorizontalScrollView horizontalscrollview;
	private ArrayList<SelectImageItem> imageItems = new ArrayList<SelectImageItem>();
	private SelectedImageAdapter selectedImageAdapter;
	private Button mSubmit;
	private File imgPath;
	public LoadingDialog mdialog;
	private List<String> mListImage = new ArrayList<String>();
	private int imgPosFlag = 0;
	private String picPath = null;
	private EditText post_title;
	private EditText post_content;
	// 图片上传表示位
	private int imageFlagNumber = 0;
	private int ctposNumber = 0;
	
	private EditText metMoney;
	private EditText metName;
	private EditText metMessage;
	private EditText metPhone;
	private List<CategoryModel> list_result = new ArrayList<CategoryModel>();
	private DialogListMsg dialogListMsg;
	
	private Button mbtnCt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creat);
		initWidget();
		initData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mIvBack:
			finish();
			break;
		case R.id.mbtnCt:
			dialogListMsg.Show();
			break;
		case R.id.mSubmit:

			Log.e("pony_log", imageFlagNumber + "");

			mdialog.show();
			UploadFileTask uploadFileTask = new UploadFileTask(this);
			uploadFileTask.execute(mListImage.get(imageFlagNumber));

			break;

		}
	}

	@Override
	public void initWidget() {
		dialogListMsg = new DialogListMsg(this);
		dialogListMsg.setTitle().setText("please select the type of the house");
		metPhone = (EditText) findViewById(R.id.metPhone);
		metMessage = (EditText) findViewById(R.id.metMessage);
		metMoney = (EditText) findViewById(R.id.metMoney);
		metName = (EditText) findViewById(R.id.metName);
		mbtnCt = (Button) findViewById(R.id.mbtnCt);
		mdialog = new LoadingDialog(this, "uploading photos...");
		mSubmit = (Button) findViewById(R.id.mSubmit);
		grid_instructor = (GridLayout) findViewById(R.id.grid_instructor);
		horizontalscrollview = (HorizontalScrollView) findViewById(R.id.horizontalscrollview);
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mTvTitle.setText("publish the advertisement of the house");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mSubmit.setOnClickListener(this);
		mbtnCt.setOnClickListener(this);
	}

	@Override
	public void initData() {
		
		MessageAction(true);
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
		initSelectedGridView();
		
		dialogListMsg.show_listview().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				ctposNumber = position;
				mbtnCt.setText(list_result.get(position).getTypeName());
				dialogListMsg.Close();

			}
		});

		dialogListMsg.submit_no().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialogListMsg.Close();
			}
		});
	}

	private void initSelectedGridView() {
		selectedImageAdapter = new SelectedImageAdapter(this, imageItems);
		SelectImageItem addItem = new SelectImageItem();
		addItem.setSid(100);// 添加的图标
		imageItems.add(addItem);
		selectedImageAdapter.notifyDataSetChanged();
		grid_instructor.setGridAdapter(selectedImageAdapter, CreatActivity.this);

	}

	@Override
	public void imageItemClick(int position, SelectImageItem imageItem) {
		if (imageItem != null) {
			int sid = imageItem.getSid();
			if (sid == 100) {

				if (CreatActivity.this.getIntent().getIntExtra("photo_message", 0) == 1) {
					goCameraActivity();
				} else {
					// 添加图片
					Intent intentImages = new Intent(CreatActivity.this, SelectImagesActivity.class);
					intentImages.putExtra("image_count", imageItems.size());
					intentImages.putExtra("max_count", "9");
					startActivityForResult(intentImages, 1);
				}
			} else {
				Intent intentPicture = new Intent(CreatActivity.this, ShowCreatePicturesActivity.class);
				intentPicture.putExtra("position", position);
				intentPicture.putExtra("piction_path", imageItems);
				startActivityForResult(intentPicture, 3);
			}
		}
	}

	private void addNewItemWithPre(String cameraPath) {

		int count = selectedImageAdapter.getCount();
		if (count > 0) {
			int selectCount = count - 1;
			SelectImageItem item = new SelectImageItem();
			item.setUrl(cameraPath);
			imageItems.add(selectCount, item);
		}
	}

	private void scrollgridView() {
		final int count = selectedImageAdapter.getCount();
		if (count > 1) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					horizontalscrollview.smoothScrollTo(2000, 0);
				}
			}, 500);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 5) {
			Log.d("camera", "" + (data == null));
			if (mCameraFile == null || !mCameraFile.exists()) {
				return;
			}
			imgPath = mCameraFile;
			mListImage.add(mCameraFile.getAbsolutePath());
			addNewItemWithPre(mCameraFile.getAbsolutePath());
		}

		if (requestCode == 1) {
			if (data != null) {

				if (0 == data.getIntExtra("action", -1)) {
					String cameraPath = data.getStringExtra("camera_path");
					addNewItemWithPre(cameraPath);

				} else if (1 == data.getIntExtra("action", -1)) {
					ArrayList<CharSequence> charSequences = data.getCharSequenceArrayListExtra("images");
					for (CharSequence ss : charSequences) {
						Log.e("pony_log", "image:" + ss.toString());
						picPath = ss.toString();
						mListImage.add(ss.toString());
						addNewItemWithPre(ss.toString());
					}
				}
				selectedImageAdapter.notifyDataSetChanged();
				scrollgridView();
			}
		}

		if (requestCode == 3) {
			if (data != null) {
				@SuppressWarnings("unchecked")
				ArrayList<SelectImageItem> imgUrls = (ArrayList<SelectImageItem>) data.getSerializableExtra("data");
				if (imgUrls != null && imgUrls.size() > 0) {
					imageItems.clear();
					imageItems.addAll(imgUrls);
					selectedImageAdapter.notifyDataSetChanged();
					scrollgridView();
				}
			}
		}
		grid_instructor.setGridAdapter(selectedImageAdapter, CreatActivity.this);
	}

	private Uri mOutPutFileUri;
	private File mCameraFile;

	// 去拍照
	private void goCameraActivity() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// dailyyoga_camera文件夹
		File parentFile = new File(Environment.getExternalStorageDirectory().toString() + "/dailyyoga_camera");
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		mCameraFile = new File(parentFile + "/" + System.currentTimeMillis() + ".jpg");
		mOutPutFileUri = Uri.fromFile(mCameraFile);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
		startActivityForResult(intent, 5);
	}

	String createTome;
	private void createTopicPost(boolean isShow) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		createTome = df.format(new Date());
		//对图片路径的处理
		String imagePath = "";
		for (int i = 0; i < mListImage.size(); i++) {
			String[] arrPath = mListImage.get(i).split("\\/");
			imagePath = arrPath[arrPath.length - 1] +","+imagePath;
		}

		AjaxParams params = new AjaxParams();
		params.put("action_flag", "addMessage");
		params.put("houseName", metName.getText().toString());
		params.put("houseMoney", metMoney.getText().toString());
		params.put("housePhone",metPhone.getText().toString());
		params.put("houseMessage",metMessage.getText().toString());
		params.put("userId",MemberUserUtils.getUid(this));
		params.put("image", imagePath.substring(0, imagePath.length()-1));
		params.put("houseCategory",list_result.get(ctposNumber).getTypeId()+"");
		params.put("houseCreatime",createTome);
		httpPost(Consts.URL + Consts.APP.HouseAction, params, Consts.actionId.resultCode, isShow, "uploading...");

	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);
		switch (actionId) {
		case Consts.actionId.resultFlag:
			if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {

				String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);
				if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
					list_result = mGson.fromJson(entry.getData(), new TypeToken<List<CategoryModel>>() {
					}.getType());
					PractitionersAdapter practitionersAdapter = new PractitionersAdapter(CreatActivity.this);
					practitionersAdapter.setData(list_result);
					dialogListMsg.show_listview().setAdapter(practitionersAdapter);
				}
			}
			break;
		case Consts.actionId.resultCode:

			mdialog.dismiss();
			ToastUtil.show(CreatActivity.this, "successfully published!");
			
			String imagePath = "";
			for (int i = 0; i < mListImage.size(); i++) {
				String[] arrPath = mListImage.get(i).split("\\/");
				imagePath = arrPath[arrPath.length - 1] + "," + imagePath;
			}
			
//			params.put("houseName", metName.getText().toString());
//			params.put("houseMoney", metMoney.getText().toString());
//			params.put("housePhone",metPhone.getText().toString());
//			params.put("houseMessage",metMessage.getText().toString());
//			params.put("userId",MemberUserUtils.getUid(this));
//			params.put("image", imagePath.substring(0, imagePath.length()-1));
//			params.put("houseCategory",list_result.get(ctposNumber).getTypeId()+"");
			
			
			HouseModel  houseModel = new HouseModel();
			houseModel.setHouseMoney(metMoney.getText().toString());
			houseModel.setHouseName(metName.getText().toString());
			houseModel.setHousePhone(metPhone.getText().toString());
			houseModel.setHouseMessage(metMessage.getText().toString());
			houseModel.setUserId(MemberUserUtils.getUid(this));
			houseModel.setHouseImage(imagePath.substring(0, imagePath.length() - 1));
			houseModel.setHouseCategory(list_result.get(ctposNumber).getTypeId()+"");
			houseModel.setHouseCreatime(createTome);
			houseModel.setHouseId( entry.getRepMsg());
			houseModel.setTypeName(mbtnCt.getText().toString());
			HouseObservable.getInstance().notifyStepChange(houseModel);
			
			
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					finish();
				}
			}, 2000);
			break;
		}
	}

	@Override
	protected void callBackAllFailure(String strMsg, int actionId) {
		super.callBackAllFailure(strMsg, actionId);
		mdialog.dismiss();
		ToastUtil.show(CreatActivity.this, strMsg);

	}

	public class UploadFileTask extends AsyncTask<String, Void, String> {
		/**
		 * 可变长的输入参数，与AsyncTask.exucute()对应
		 */
		private Activity context = null;

		public UploadFileTask(Activity ctx) {
			this.context = ctx;

		}

		@Override
		protected void onPostExecute(String result) {
			imageFlagNumber++;
			if (UploadUtils.SUCCESS.equalsIgnoreCase(result)) {
				if (imageFlagNumber < mListImage.size()) {
					UploadFileTask uploadFileTask = new UploadFileTask(CreatActivity.this);
					uploadFileTask.execute(mListImage.get(imageFlagNumber));
				} else if (imageFlagNumber == mListImage.size()) {
					// 返回HTML页面的内容
					createTopicPost(true);
				}
			} else {
				ToastUtil.show(CreatActivity.this, " photo upload failed!");
			}
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected String doInBackground(String... params) {
			File file = new File(params[0]);
			return UploadUtils.uploadFile(file, Consts.URL + Consts.APP.HouseAction+"?action_flag=addMessage");
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

	}
	

	private void MessageAction(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "listTypePhoneMessage");
		httpPost(Consts.URL + Consts.APP.ShopAction, params, Consts.actionId.resultFlag, isShow, "loading...");
	}
	
}
