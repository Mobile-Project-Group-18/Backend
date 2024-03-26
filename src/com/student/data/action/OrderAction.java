package com.student.data.action;

import java.io.IOException;
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

import net.sf.json.JSONObject;

import com.student.data.dao.OrderDao;

public class OrderAction extends HttpServlet {

	private OrderDao orderDao;

	public OrderAction() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String action_flag = request.getParameter("action_flag");
		if (action_flag.equals("addOrder")) {
			addOrderMessage(request, response);
		} else if (action_flag.equals("userOrderListMessage")) {
			userOrderListMessage(request, response);
		}else if (action_flag.equals("checkPay")) {
			checkPay(request, response);
		}else if (action_flag.equals("updateOrderState")) {
			updateOrderState(request, response);
		}else if (action_flag.equals("orderListPcMessage")) {
			orderListPcMessage(request, response);
		} else if (action_flag.equals("myapplyMessage")) {
			myapplyMessage(request, response);
		} else if (action_flag.equals("listMyUserOrderZeKe")) {
			listMyUserOrderZeKe(request, response);
		}
			
	}

	public void init() throws ServletException {
		orderDao = new OrderDao();
	}
	
	private void listMyUserOrderZeKe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String applyHouseUserId = request.getParameter("applyHouseUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(applyHouseUserId);

		List<Map<String, Object>> list = orderDao.listMyUserOrderZeKe(params);
		// 生成json字符串
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}
	
	
	private void myapplyMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String applyUserId = request.getParameter("applyUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(applyUserId);

		List<Map<String, Object>> list = orderDao.listMyUserOrder(params);
		// 生成json字符串
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}
	private void orderListPcMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = orderDao.listOrderPcnewMessage();
		System.out.println(list.size());
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../orderMessage.jsp").forward(request, response);

	}
	
	
	private void updateOrderState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String applyState = request.getParameter("applyState");
		String applyId = request.getParameter("applyId");
		String applyHouseUserId = request.getParameter("applyHouseUserId");
		
		List<Object> params = new ArrayList<Object>();
		params.add(applyState);
		params.add(applyId);
		params.add(applyHouseUserId);
		boolean flag = orderDao.updateOrderState(params);
		if (flag) {

			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "处理成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}
	
	private void checkPay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String orderShopUserId = request.getParameter("orderShopUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(orderShopUserId);

		List<Map<String, Object>> list = orderDao.listCheckPayOrder(params);
		// 生成json字符串
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void userOrderListMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String applyUserId = request.getParameter("applyUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(applyUserId);

		List<Map<String, Object>> list = orderDao.listMyUserOrder(params);
		// 生成json字符串
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addOrderMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String path = request.getContextPath();
		String applyHouseId = request.getParameter("applyHouseId");
		String applyHouseName = request.getParameter("applyHouseName");
		String applyHouseMoney = request.getParameter("applyHouseMoney");
		String applyUserId = request.getParameter("applyUserId");
		String applyUserName = request.getParameter("applyUserName");
		String applyHouseUserId = request.getParameter("applyHouseUserId");

		List<Object> params = new ArrayList<Object>();

		params.add(applyUserId);
		params.add(applyUserName);
		params.add(applyHouseId);
		params.add(applyHouseName);
		params.add(applyHouseMoney);
		params.add(applyHouseUserId);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		params.add(df.format(new Date()));
		params.add("1");

		boolean flag = orderDao.addOrder(params);
		JSONObject jsonmsg = new JSONObject();
		if (flag) {
			jsonmsg.put("repMsg", "预约已提交");
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

}