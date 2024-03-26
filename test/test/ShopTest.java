package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.student.data.dao.HouseDao;
import com.student.data.dao.OrderDao;
import com.student.data.dao.RegisterDao;
import com.student.data.dao.ShopDao;

public class ShopTest {

	private HouseDao shopDao;

	@Test
	public void Reg() {
		shopDao = new HouseDao();
		List<Object> params = new ArrayList<Object>();
		params.add("9");
		List<Map<String, Object>> list = shopDao.queryMessage("公寓");
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "ok");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		 

	}
}
