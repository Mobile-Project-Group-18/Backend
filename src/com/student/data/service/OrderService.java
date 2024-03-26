package com.student.data.service;

import java.util.List;
import java.util.Map;

public interface OrderService {
	
	
	public boolean deleteState(List<Object> params);
	
	
	public boolean addState(List<Object> params);
	
	public boolean addOrder(List<Object> params);
	
	public boolean deleteOrder(List<Object> params);
	
	public Map<String, Object> queryOne(List<Object> params);

	public Map<String, Object> queryId(List<Object> params);

	public List<Map<String, Object>> listOrder(String proname, int start, int end);

	
	public int getItemCount();
	

}
