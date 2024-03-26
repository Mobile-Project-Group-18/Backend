package com.wang.config;

import android.os.Environment;

/**
 * 
 * @author WangKui
 * 
 */
public class Consts {

	/**
	 * 请求超时
	 */
	public static int TIME_OUT = 30000;
	/**
	 * 3秒后自动进入其他页面
	 */
	public final static int SPLASH_DISPLAY_LENGHT = 3000; //

	public static String IMAGEPATH = Environment.getExternalStorageDirectory() + "/XCI/DTRM/IMAGES/";

	/**
	 * page
	 */
	public final static int PAGE = 1; //

	/**
	 * 
	 * 
	 * 访问地址(wxh)
	 */
	public final static String URL = "http://192.168.56.1:8080/RentalService/";
	public static final String requestURL = "http://192.168.56.1:8080/RentalService/servlet/FileImageUploadServlet";
	public static final String requestURL_UPDATE = "http://192.168.56.1:8080/RentalService/servlet/FileImageUploadUpdateServlet";
	public final static String URL_IMAGE = "http://192.168.56.1:8080/RentalService/upload/";

	public final static String URL_IMAGE_LOCAL = "http://192.168.56.1:8080/yn/upload/";
	

	/**
	 * 分页加载 默认每次加载10�?
	 */
	public final static int PAGE_SIZE = 10;

	public final static int USERTYPE = 1;


	public static class APP {

		/**
		 * 登录
		 */
		public static final String RegisterAction = "servlet/RegisterAction";
		public static final String CourseAction = "servlet/CourseAction";
		public static final String MessageAction = "servlet/MessageAction";
		public static final String JobAction = "servlet/JobAction";
		public static final String PersonAction = "servlet/PersonAction";
		public static final String PersonToJobAction = "servlet/PersonToJobAction";
		
		
		
		/**
		 * 登录
		 */
		public static final String StudentAction = "servlet/StudentAction";
		public static final String TeacherAction = "servlet/TeacherAction";
		public static final String HouseAction = "servlet/HouseAction";
		public static final String OrderAction = "servlet/OrderAction";
		public static final String SignAction = "servlet/SignAction";
		public static final String LeaveAction = "servlet/LeaveAction";
		public static final String TypeAction = "servlet/TypeAction";
		public static final String ScoreAction = "servlet/ScoreAction";
		
		public static final String NewsAction = "servlet/NewsAction";
		
		
		
		
		/**
		 * 获取指引列表
		 */
		public static final String FileImageUploadServlet = "servlet/FileImageUploadServlet";
		public static final String FileImageUploadUpdateServlet = "servlet/FileImageUploadUpdateServlet";
		public static final String UserServlet = "servlet/UserServlet";
		
		public static final String ShopAction = "servlet/ShopAction";
		
		
		
	}

	public static class actionId {
		/**
		 * 更多 帮助
		 */
		public static final int resultFlag = 1;
		public static final int resultCode = 2;
		public static final int resultMsg = 3;
		public static final int resultSix = 6;
		public static final int resultSeven = 7;
	}
}
