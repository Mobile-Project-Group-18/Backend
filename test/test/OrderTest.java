package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.student.data.dao.OrderDao;

public class OrderTest {

	private OrderDao orderDao;

	@Test
	public void Reg() {
		orderDao = new OrderDao();
		List<Object> params = new ArrayList<Object>();
		params.add(17);
		List<Map<String, Object>> list = orderDao.listMyUserOrderZeKe(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "ok");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		 

	}
}
