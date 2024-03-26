package com.student.data.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.student.data.service.OrderService;
import com.student.jdbc.JdbcUtils;

public class OrderDao implements OrderService {
	private JdbcUtils jdbcUtils;

	public OrderDao() {
		jdbcUtils = new JdbcUtils();
	}
	
	
	public List<Map<String, Object>> listMyUserOrderZeKe(List<Object> params) {
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;
		
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from applymsg  where  applyHouseUserId = ?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);
				List<Object> paramsFocus = new ArrayList<Object>();
				paramsFocus.clear();
				paramsFocus.add(list.get(i).get("applyHouseId") + "");
				Map<String, Object> houseMessage = queryHouse(paramsFocus);
				mapResult.put("houseMessage", houseMessage);
				
				
				List<Object> paramsFocusUser = new ArrayList<Object>();
				paramsFocusUser.clear();
				paramsFocusUser.add(list.get(i).get("applyUserId") + "");
				Map<String, Object> queryUser = queryUser(paramsFocusUser);
				
				mapResult.put("userMessage", queryUser);
				
				listResult.add(mapResult);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}
	
	public Map<String, Object> queryUser(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from user where uid=?";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}
	
	
	
	
	
	
	public List<Map<String, Object>> listOrderPcnewMessage() {
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from ordermsg";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
			
			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);
				List<Object> paramsFocus = new ArrayList<Object>();
				paramsFocus.clear();
				paramsFocus.add(list.get(i).get("orderShopId") + "");
				Map<String, Object> foodMsg = queryHouse(paramsFocus);
				mapResult.put("shopName", foodMsg.get("sName"));
				mapResult.put("shopPhone", foodMsg.get("sPhone"));
				listResult.add(mapResult);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}
	public boolean updateOrderState(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  applymsg set applyState =? where applyId = ? and applyHouseUserId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	public List<Map<String, Object>> listCheckPayOrder(List<Object> params) {
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;
		
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from ordermsg  where  orderShopUserId = ?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);
				List<Object> paramsFocus = new ArrayList<Object>();
				paramsFocus.clear();
				paramsFocus.add(list.get(i).get("orderShopId") + "");
				Map<String, Object> foodMsg = queryHouse(paramsFocus);
				mapResult.put("shopMessage", foodMsg);
				listResult.add(mapResult);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listMyUserOrder(List<Object> params) {
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;
		
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from applymsg  where  applyUserId = ?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);
				List<Object> paramsFocus = new ArrayList<Object>();
				paramsFocus.clear();
				paramsFocus.add(list.get(i).get("applyHouseId") + "");
				Map<String, Object> houseMessage = queryHouse(paramsFocus);
				mapResult.put("houseMessage", houseMessage);
				listResult.add(mapResult);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}
	public Map<String, Object> queryHouse(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from housemsg where houseId=?";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}
	
	public boolean updateUserState(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  ordermsg set orderUserState =? where orderId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	
	
	
	public Map<String, Object> queryOrder(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from ordermsg where  orderState = 1 and orderUserId=  ? and orderFoodId = ?";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}
	
	
	public boolean updateAddNumber(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  ordermsg set orderNumber =?  where orderState = 1 and orderId = ? and orderUserId=  ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	

	public boolean updateNumber(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  ordermsg set orderNumber =?  where orderState = 1 and orderId = ? and orderUserId=  ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean queryCheck(List<Object> params) {
		boolean flag = false;
		String sql = "select * from ordermsg where orderState = 1 and orderUserId=  ? and orderFoodId = ?";
		try {
			jdbcUtils.getConnection();
			Map<String, Object> map = jdbcUtils.findSimpleResult(sql, params);
			flag = !map.isEmpty() ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listOrderDeleteMessage(List<Object> params) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from ordermsg where orderState = 1 and orderUserId=  ? and orderFoodId = ?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listCarOrder(List<Object> params) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select *,count(orderFoodId) as foodNumber from ordermsg  where orderState = 1 and orderUserId = ? group by orderFoodId,orderFoodName";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public Map<String, Object> listOrderNumberPhone(List<Object> params) {

		Map<String, Object> map = null;
		String sql = "select  sum(orderNumber) as foodNumber from ordermsg where orderState = 1 and orderUserId=  ?";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public List<Map<String, Object>> listOrderPcMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from ordermsg ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean deleteState(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "delete from checkorder where oid=?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean addState(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into checkorder (hid,oid,cstate,uid) values (?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	public boolean updateState(String userId,String foodId,String orderTotalMoney,String orderAddress,String orderPayType) {
		boolean flag = false;
		try {
			String sql = "update ordermsg set orderState=2 ";
			
			StringBuffer buffer = new StringBuffer(sql);
			List<Object> paramsSql = new ArrayList<Object>();
			if (userId != null) {
				buffer.append(" ,  orderTotalMoney = ? , orderAddress = ? , orderPayType = ?  where orderUserId = ? and  orderFoodId in ("+foodId+")");
				paramsSql.add(orderTotalMoney);
				paramsSql.add(orderAddress);
				paramsSql.add(orderPayType);
				paramsSql.add(userId);
			}
			
			System.out.println(buffer.toString());
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(buffer.toString(), paramsSql);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean addOrder(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into applymsg (applyUserId,applyUserName,applyHouseId,applyHouseName,applyHouseMoney,applyHouseUserId,applyTime,applyState) values  (?,?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean deleteOrder(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "delete from ordermsg where orderId=?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	@Override
	public Map<String, Object> queryOne(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select ordermsg.oid from ordermsg where uid=? and hid=?";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	@Override
	public Map<String, Object> queryId(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select uid from ordermsg where uname=? and upswd=?";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public List<Map<String, Object>> listOrder(String proname, int start, int end) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from ordermsg where (1=1) ";
		// limit ?,?
		StringBuffer buffer = new StringBuffer(sql);
		List<Object> params = new ArrayList<Object>();
		if (proname != null) {
			buffer.append(" and oid like ? ");
			params.add("%" + proname + "%");
		}
		buffer.append("limit ?,? ");
		params.add(start);
		params.add(end);
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(buffer.toString(), params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listOrderMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from ordermsg  where orderState = 2";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listMyOrderMessagePhone(List<Object> params) {
		// TODO Auto-generated method stub


		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String sql = "select *  from ordermsg  where orderState = 2 and orderUserId = ? ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listOrderMessagePhone(List<Object> params) {
		// TODO Auto-generated method stub


		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String sql = "select *,count(orderFoodId) as foodNumber from ordermsg  where orderState = 2 and orderUserId = ? group by orderFoodId,orderFoodName";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public Map<String, Object> queryBook(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from bookmsg where bookId=?";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public int getItemCount() {
		int result = 0;
		Map<String, Object> map = null;
		String sql = " select count(*) mycount from ordermsg ";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, null);
			result = Integer.parseInt(map.get("mycount").toString());
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		// TODO Auto-generated method stub
		return result;
	}


}
