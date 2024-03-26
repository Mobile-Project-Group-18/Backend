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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.sf.json.JSONObject;

import javax.servlet.ServletConfig;

import com.student.data.dao.StudentDao;

public class StudentAction extends HttpServlet {

	private StudentDao studentDao;
	private static final long serialVersionUID = 1L;
	private ServletFileUpload upload;
	private final long MAXSize = 4194304 * 2L;// 4*2MB
	private String filedir = null;

	public StudentAction() {
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
		// queryStudent&studentId
		String action_flag = request.getParameter("action_flag");
		if (action_flag.equals("addMessage")) {
			addMessage(request, response);
		} else if (action_flag.equals("listMessage")) {
			listMessage(request, response);
		} else if (action_flag.equals("studentReg")) {
			studentReg(request, response);
		} else if (action_flag.equals("queryStudent")) {
			queryStudent(request, response);
		} else if (action_flag.equals("updateStudentState")) {
			updateStudentState(request, response);
		} else if (action_flag.equals("studentLogin")) {
			studentLogin(request, response);
		} else if (action_flag.equals("updatePswd")) {
			updatePswd(request, response);
		} else if (action_flag.equals("updateAddress")) {
			updateAddress(request, response);
		} else if (action_flag.equals("deleteStudnet")) {
			deleteStudnet(request, response);
		}
	}

	public void init(ServletConfig config) throws ServletException {
		FileItemFactory factory = new DiskFileItemFactory();// Create a factory
		this.upload = new ServletFileUpload(factory);// Create a new file upload
		this.upload.setSizeMax(this.MAXSize);// Set overall request size
		filedir = config.getServletContext().getRealPath("upload");
		studentDao = new StudentDao();
	}
	
	private void deleteStudnet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String studentId = request.getParameter("studentId");
		List<Object> params = new ArrayList<Object>();
		params.add(studentId);
		boolean flag = studentDao.deleteStudnet(params);
		if (flag) {
			listMessage(request, response);
		} else {
			System.out.println("失败了");
		}

	}
	
	
	private void updateAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String studentSchoolAddress = request.getParameter("studentSchoolAddress");
		String studentId = request.getParameter("studentId");
		List<Object> params = new ArrayList<Object>();
		params.add(studentSchoolAddress);
		params.add(studentId);
		boolean flag = studentDao.updateAddress(params);

		if (flag) {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
//			response.sendRedirect(path + "/servlet/NoticeAction?action_flag=listMessage");

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
//			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

		
	}
	private void updatePswd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String studentPswd = request.getParameter("studentPswd");
		String studentId = request.getParameter("studentId");
		List<Object> params = new ArrayList<Object>();
		params.add(studentPswd);
		params.add(studentId);
		boolean flag = studentDao.updatePswd(params);

		if (flag) {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
//			response.sendRedirect(path + "/servlet/NoticeAction?action_flag=listMessage");

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
//			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

		
	}
	
	private void studentLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String studentPhone = request.getParameter("studentPhone");
		String studentPswd = request.getParameter("studentPswd");

		List<Object> params_check_login = new ArrayList<Object>();
		params_check_login.add(studentPhone);
		params_check_login.add(studentPswd);
		boolean flag = studentDao.studentLogin(params_check_login);
		if (flag) {
			Map<String, Object> map = studentDao.queryLoginStudent(params_check_login);

			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "登录成功");
			jsonmsg.put("repCode", "666");
			jsonmsg.put("data", map);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "用户名或密码不正确");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}
	}

	private void updateStudentState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String studentState = request.getParameter("studentState");
		String studentId = request.getParameter("studentId");
		
		System.out.println(studentState);
		System.out.println(studentId);
		List<Object> params = new ArrayList<Object>();
		params.add(studentState);
		params.add(studentId);
		boolean flag = studentDao.updateStudentState(params);
		if (flag) {

			listMessage(request, response);

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void queryStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String studentId = request.getParameter("studentId");
		String state = request.getParameter("state");
		List<Object> params = new ArrayList<Object>();
		params.add(studentId);
		Map<String, Object> mapmsg = studentDao.queryStudent(params);
		request.setAttribute("mapmsg", mapmsg);
		if(state.equals("2")){
			request.getRequestDispatcher("../queryRegStudent.jsp").forward(request, response);	
		}else{
			request.getRequestDispatcher("../lookRegStudent.jsp").forward(request, response);
		}


	}

	private void studentReg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String studentNickname = request.getParameter("studentNickname");
		String studentPhone = request.getParameter("studentPhone");
		String studentPswd = request.getParameter("studentPswd");
		String studentSchoolName = request.getParameter("studentSchoolName");
		String studentSchoolAddress = request.getParameter("studentSchoolAddress");
		String studentIdCard = request.getParameter("studentIdCard");
		String studentSchoolCard = request.getParameter("studentSchoolCard");
		String studentSchoolImage = request.getParameter("studentSchoolImage");

		System.out.println("studentNo:" + studentSchoolCard);

		String imagePath = null;
		if (studentSchoolCard == null) {
			try {
				List<FileItem> items = this.upload.parseRequest(request);
				if (items != null && !items.isEmpty()) {
					for (FileItem fileItem : items) {
						String filename = fileItem.getName();
						String filepath = filedir + File.separator + filename;
						System.out.println("文件保存路径为:" + filepath);
						File file = new File(filepath);
						InputStream inputSteam = fileItem.getInputStream();
						BufferedInputStream fis = new BufferedInputStream(inputSteam);
						FileOutputStream fos = new FileOutputStream(file);
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
					}
				}

			} catch (FileUploadException e) {
				e.printStackTrace();
				out.write("上传文件失败:" + e.getMessage());
			}
		} else {
			List<Object> paramsCheck = new ArrayList<Object>();
			paramsCheck.add(studentSchoolCard);
			boolean checkStudent = studentDao.checkStudent(paramsCheck);

			if (checkStudent) {
				List<Object> params = new ArrayList<Object>();
				params.add(studentNickname + "");
				params.add(studentPhone + "");
				params.add(studentPswd + "");
				params.add(studentSchoolName + "");
				params.add(studentSchoolAddress);
				params.add(studentIdCard);
				params.add(studentSchoolImage);
				params.add("2");
				params.add(studentSchoolCard);
				boolean flag = studentDao.studentReg(params);
				System.out.println(flag + "");
				if (flag) {
					JSONObject jsonmsg = new JSONObject();
					jsonmsg.put("repMsg", "注册成功");
					jsonmsg.put("repCode", "666");
					response.getWriter().print(jsonmsg);// 将路径返回给客户端
				} else {
					JSONObject jsonmsg = new JSONObject();
					jsonmsg.put("repMsg", "注册失败");
					jsonmsg.put("repCode", "111");
					response.getWriter().print(jsonmsg);// 将路径返回给客户端
				}
			} else {
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "学生学号不正确");
				jsonmsg.put("repCode", "111");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
			}

		}

	}

	private void listMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = studentDao.listMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../studentMessage.jsp").forward(request, response);

	}

	private void addMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String studentName = request.getParameter("studentName");

		SimpleDateFormat dfNo = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		List<Object> params = new ArrayList<Object>();
		params.add("stu" + dfNo.format(new Date()));
		params.add(studentName);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		params.add(df.format(new Date()));
		System.out.println("stu" + dfNo.format(new Date()));
		System.out.println(studentName);
		params.add("1");
		boolean flag = studentDao.addMessage(params);
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
