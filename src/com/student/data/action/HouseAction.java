package com.student.data.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.student.data.dao.HouseDao;

public class HouseAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletFileUpload upload;
	private final long MAXSize = 4194304 * 2L;// 4*2MB
	private String filedir = null;
	private HouseDao hobbyDao;

	public HouseAction() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getContextPath();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String action_flag = request.getParameter("action_flag");
		if (action_flag.equals("queryMRMessage")) {
			queryMRMessage(request, response);
		} else if (action_flag.equals("addMessage")) {
			addMessage(request, response);
		} else if (action_flag.equals("listInterestMessage")) {
			listInterestMessage(request, response);
		} else if (action_flag.equals("listInterestUserMessage")) {
			listInterestUserMessage(request, response);
		} else if (action_flag.equals("deleteInterest")) {
			deleteInterest(request, response);
		} else if (action_flag.equals("deletePcInterest")) {
			deletePcInterest(request, response);
		} else if (action_flag.equals("listHuoDongMessage")) {
			listHuoDongMessage(request, response);
		} else if (action_flag.equals("deleteHobby")) {
			deleteHobby(request, response);
		}  else if (action_flag.equals("queryMessage")) {
			queryMessage(request, response);
		}

	}

	
	public void init(ServletConfig config) throws ServletException {
		FileItemFactory factory = new DiskFileItemFactory();// Create a factory
		this.upload = new ServletFileUpload(factory);// Create a new file upload
		this.upload.setSizeMax(this.MAXSize);// Set overall request size
		filedir = config.getServletContext().getRealPath("upload");
		System.out.println("filedir=" + filedir);
		hobbyDao = new HouseDao();
	}
	private void queryMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 已经进行分页之后的数据集合
		String searchmessage = request.getParameter("searchmessage");

//		List<Object> params = new ArrayList<Object>();
//		params.add(uid);
		List<Map<String, Object>> list = hobbyDao.queryMessage(searchmessage);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端
	}
	private void deleteHobby(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String hobbyId = request.getParameter("hobbyId");
		List<Object> params = new ArrayList<Object>();
		params.add(hobbyId);
		boolean flag = hobbyDao.deletehobbymsg(params);
		if (flag) {
			listHuoDongMessage(request, response);
		}

	}

	private void listHuoDongMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = hobbyDao.listHuoDongMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../hobbyMessage.jsp").forward(request, response);

	}

	private void deletePcInterest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String interestId = request.getParameter("interestId");
		List<Object> params = new ArrayList<Object>();
		params.add(interestId);
		boolean flag = hobbyDao.deleteInter(params);
		if (flag) {
			listMessage(request, response);
		}

	}

	private void deleteInterest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String interestId = request.getParameter("interestId");
		List<Object> params = new ArrayList<Object>();
		params.add(interestId);
		boolean flag = hobbyDao.deleteInter(params);
		if (flag) {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "删除成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}
	}

	private void listInterestUserMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String interestUserId = request.getParameter("interestUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(interestUserId);

		List<Map<String, Object>> flagFood = hobbyDao.listInterestUserMessage(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void listInterestMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Map<String, Object>> flagFood = hobbyDao.listInterestMessage();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sCategory = request.getParameter("houseCategory");
		String sName = request.getParameter("houseName");
		String sMoney = request.getParameter("houseMoney");
		String sPhone = request.getParameter("housePhone");
		String sMessage = request.getParameter("houseMessage");
		String userId = request.getParameter("userId");
		String image = request.getParameter("image");
		String houseCreatime = request.getParameter("houseCreatime");
		
		String imagePath = null;
		if (sName == null) {
			try {
				List<FileItem> items = this.upload.parseRequest(request);
				if (items != null && !items.isEmpty()) {
					for (FileItem fileItem : items) {
						String filename = fileItem.getName();
//						String filepath = filedir + File.separator + filename;

						String imgPath = "E:\\2017-2018code\\A705Rental\\RentalService\\WebRoot\\upload";
						
						System.out.println("文件保存路径为:" + imgPath + "/" + filename);
						
						File real_path = new File(imgPath + "/" + filename);
						// File file = new File(filepath);
						InputStream inputSteam = fileItem.getInputStream();
						BufferedInputStream fis = new BufferedInputStream(inputSteam);
						FileOutputStream fos = new FileOutputStream(real_path);
						int f;
						while ((f = fis.read()) != -1) {
							fos.write(f);
						}
						fos.flush();
						fos.close();
						fis.close();
						inputSteam.close();
						System.out.println("文件：" + filename + "上传成功!");
						imagePath = filename;
						
//						String filename = fileItem.getName();
//						String filepath = filedir + File.separator + filename;
//						System.out.println("文件保存路径为:" + filepath);
//						File file = new File(filepath);
//						InputStream inputSteam = fileItem.getInputStream();
//						BufferedInputStream fis = new BufferedInputStream(inputSteam);
//						FileOutputStream fos = new FileOutputStream(file);
//						int f;
//						while ((f = fis.read()) != -1) {
//							fos.write(f);
//						}
//						fos.flush();
//						fos.close();
//						fis.close();
//						inputSteam.close();
//						System.out.println("文件：" + filename + "上传成功!");
//						imagePath = filename;

					}
				}

			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		} else {

			List<Object> params = new ArrayList<Object>();
			params.add(sName+"");
			params.add(sMoney+"");
			params.add(sPhone+"");
			params.add(sMessage+"");
			params.add(image);
			params.add(houseCreatime);
			params.add(userId+"");
			params.add(1+"");
			params.add(sCategory);
			
			boolean flag = hobbyDao.addMessage(params);
			System.out.println(flag+"");
			if (flag) {
				
				List<Object> paramsCheck = new ArrayList<Object>();
				paramsCheck.add(houseCreatime);
				Map<String, Object> map = hobbyDao.queryhourse(paramsCheck);
				
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg",map.get("houseId"));
				jsonmsg.put("repCode", "666");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
			} else {
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "上传文件失败");
				jsonmsg.put("repCode", "111");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
			}
			
		
		}
	}

	private void queryMRMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String medicalRecordPatientId = request.getParameter("medicalRecordPatientId");
		String appointmentId = request.getParameter("appointmentId");
		List<Object> params = new ArrayList<Object>();
		params.add(medicalRecordPatientId);
		params.add(appointmentId);
		Map<String, Object> flagFood = hobbyDao.queryMRMessage(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addMRMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String medicalRecordPatientId = request.getParameter("medicalRecordPatientId");
		String medicalRecordPatientName = request.getParameter("medicalRecordPatientName");
		String medicalRecordPatientPhone = request.getParameter("medicalRecordPatientPhone");
		String medicalRecordPatientTime = request.getParameter("medicalRecordPatientTime");
		String medicalRecordPatientMessage = request.getParameter("medicalRecordPatientMessage");
		String medicalRecordDoctorId = request.getParameter("medicalRecordDoctorId");
		String medicalRecordDoctorName = request.getParameter("medicalRecordDoctorName");
		String appointmentId = request.getParameter("appointmentId");

		List<Object> params = new ArrayList<Object>();
		params.add(medicalRecordPatientId);
		params.add(medicalRecordPatientName);
		params.add(medicalRecordPatientPhone);
		params.add(medicalRecordPatientTime);
		params.add(medicalRecordPatientMessage);
		params.add(medicalRecordDoctorId);
		params.add(medicalRecordDoctorName);
		params.add(appointmentId);

		boolean flag = hobbyDao.addMRMessage(params);
		JSONObject jsonmsg = new JSONObject();
		if (flag) {
			jsonmsg.put("repMsg", "提交成功");
			jsonmsg.put("repCode", "666");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		} else {
			jsonmsg.put("repMsg", "提交失败");
			jsonmsg.put("repCode", "111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		}
	}

	private void listDoctorQueryAppointmentMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String appointmentDoctorId = request.getParameter("appointmentDoctorId");
		List<Object> params = new ArrayList<Object>();
		params.add(appointmentDoctorId);
		List<Map<String, Object>> flagFood = hobbyDao.listDoctorAppointmentmsgMessage(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void listAppointmentMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String appointmentUserId = request.getParameter("appointmentUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(appointmentUserId);
		List<Map<String, Object>> flagFood = hobbyDao.listAppointmentmsgMessage(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addAppointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String appointmentDoctorId = request.getParameter("appointmentDoctorId");
		String appointmentDoctorName = request.getParameter("appointmentDoctorName");
		String appointmentUserId = request.getParameter("appointmentUserId");
		String appointmentUserName = request.getParameter("appointmentUserName");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式

		List<Object> paramsCheck = new ArrayList<Object>();
		paramsCheck.add(appointmentDoctorId);
		paramsCheck.add(df.format(new Date()));
		int appNumber = hobbyDao.getItemCount(paramsCheck);

		List<Object> params = new ArrayList<Object>();
		params.add(appointmentDoctorId);
		params.add(appointmentDoctorName);
		params.add(appointmentUserId);
		params.add(appointmentUserName);
		params.add(df.format(new Date()));

		if (appNumber == 0) {
			params.add("1");
		} else {
			params.add(appNumber + 1);
		}

		boolean flag = hobbyDao.addAppointment(params);
		JSONObject jsonmsg = new JSONObject();
		if (flag) {
			jsonmsg.put("repMsg", "预约成功");
			jsonmsg.put("repCode", "666");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		} else {
			jsonmsg.put("repMsg", "预约失败");
			jsonmsg.put("repCode", "111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		}
	}


	private void listSearchMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String searchMsg = request.getParameter("searchMsg");
		List<Map<String, Object>> list = hobbyDao.listSearchMessage(searchMsg);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void deleteMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String flowerId = request.getParameter("flowerId");
		List<Object> params = new ArrayList<Object>();
		params.add(flowerId);
		boolean flag = hobbyDao.deleteMessage(params);
		if (flag) {
			listMessage(request, response);
		}

	}

	private void updateReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String rId = request.getParameter("rId");
		String replyContent = request.getParameter("replyContent");
		List<Object> params = new ArrayList<Object>();
		params.add(replyContent);
		params.add(rId);
		boolean flag = hobbyDao.updateMessage(params);
		if (flag) {
			List<Map<String, Object>> list = hobbyDao.listReviewMessage();
			request.setAttribute("listMessage", list);
			request.getRequestDispatcher("../reviewListMessage.jsp").forward(request, response);
		}

	}

	private void listPhoneMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Map<String, Object>> flagFood = hobbyDao.listPhoneMessage();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void listMessageChoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = hobbyDao.listMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../formJob.jsp").forward(request, response);

	}

	private void listMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = hobbyDao.listMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../interMessage.jsp").forward(request, response);

	}

	private void addOldMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String naviName = request.getParameter("naviName");
		String naviJD = request.getParameter("naviJD");
		String naviWD = request.getParameter("naviWD");
		List<Object> params = new ArrayList<Object>();
		params.add(naviName);
		params.add(naviJD);
		params.add(naviWD);
		boolean flag = hobbyDao.addMessage(params);
		if (flag) {
			listMessage(request, response);
		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

}
