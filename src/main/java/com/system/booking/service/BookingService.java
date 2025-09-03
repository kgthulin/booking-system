package com.system.booking.service;

import java.util.Map;

public interface BookingService {

	void createClass(Long classId, String name, Integer available);
	boolean hasBooked(String classId, String userId);
	Map<Object, Object> getClass(String classId);
	boolean bookClass(String classId, String userId);
	boolean cancelClass(String classId, String userId);
	void createStringKey(String key, String value);
}
