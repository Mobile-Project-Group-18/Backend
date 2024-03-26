package com.wang.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wang.model.SelectImageItem;
import com.wang.model.ViewHolder;
import com.wang.util.ImageLoaderOptions;
import com.rental.R;

public class SelectedImageAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<SelectImageItem> mData;

	public SelectedImageAdapter(Context context, ArrayList<SelectImageItem> data) {
		mContext = context;
		mData = data;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public SelectImageItem getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_selected_item, null);

			View imageItem = ViewHolder.get(convertView, R.id.img_item);
			ViewGroup.LayoutParams layoutParams = imageItem.getLayoutParams();

			layoutParams.width = getImageWidth();
			layoutParams.height = getImageWidth();
			imageItem.setLayoutParams(layoutParams);
		}

		final ImageView imageItem = ViewHolder.get(convertView, R.id.img_item);
		SelectImageItem selectImageItem = mData.get(position);

		if (selectImageItem != null) {
			if (selectImageItem.getSid() == 100) {
				ImageLoader.getInstance().displayImage(
						"",
						imageItem,
						ImageLoaderOptions.getCommonNomemOption(R.drawable.add_image_selector, R.drawable.add_image_selector,
								R.drawable.add_image_selector));
			} else {
				String cameraPath = selectImageItem.getUrl();
				Bitmap bitmap = BitmapFactory.decodeFile(cameraPath);
				Bitmap mark = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.def_stc_icon);
				double width;
				double height;
				if (bitmap.getWidth() < 600) {
					width = 150;
					height = 150;
				} else {
					width = bitmap.getWidth() * 0.3;
					height = bitmap.getWidth() * 0.3;
				}
				imageItem.setImageBitmap(loadResBitmap(cameraPath,3));
				// imageItem.setImageBitmap(createBitmap(ImageUtil.zoomImage(bitmap,
				// width, height)));

			}
		}

		return convertView;
	}

	public static Bitmap loadResBitmap(String path, int scalSize) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		options.inSampleSize = scalSize;
		Bitmap bmp = BitmapFactory.decodeFile(path, options);
		return bmp;
	}

	private int getImageWidth() {
		DisplayMetrics metric = mContext.getResources().getDisplayMetrics();
		final float scale = mContext.getResources().getDisplayMetrics().density;
		int dev = (int) (50 * scale + 0.5f);

		return (metric.widthPixels - dev) / (mContext.getResources().getBoolean(R.bool.isSw600) ? 9 : 4); //
	}

	/**
	 * 创建需要水印的的图片
	 * 
	 * @return
	 */
	private Bitmap createBitmap(Bitmap bitmap) {

		TextPaint textPaint = new TextPaint();
		textPaint.setTextSize(30.0f);// 字体大小
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
		textPaint.setColor(Color.RED);// 采用的颜色
		String test = "企业：Pony开发部\n位置：西安市雁塔区含光南路\n时间：2016-11-17";

		/**
		 * source : 需要分行的字符串 paint : 画笔对象 width : layout的宽度，字符串超出宽度时自动换行。 align
		 * : layout的对其方式，有ALIGN_CENTER， ALIGN_NORMAL， ALIGN_OPPOSITE 三种。
		 * spacingmult : 相对行间距，相对字体大小，1.5f表示行间距为1.5倍的字体高度。 spacingadd :
		 * 在基础行距上添加多少,实际间距为spacingmult spacingadd 的和 includepad :
		 * 不明，希望知道的朋友可以告知一下
		 */

		StaticLayout layout = new StaticLayout(test, textPaint, (int) (bitmap.getWidth() * 0.3), Alignment.ALIGN_NORMAL, 1.5F, 0.0F, true);
		int width = bitmap.getWidth(), hight = bitmap.getHeight();
		Bitmap icon = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
		Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上

		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		photoPaint.setFilterBitmap(true);// 过滤一些

		Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
		canvas.drawBitmap(bitmap, src, dst, photoPaint);// 将photo 缩放或则扩大到
		canvas.translate(20, 80);
		layout.draw(canvas);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return icon;
	}

}
