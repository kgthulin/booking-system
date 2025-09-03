package com.system.booking.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingServiceImpl implements BookingService {
	
	private final StringRedisTemplate redisTemplate;
	
	public BookingServiceImpl(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void createClass(Long classId, String name, Integer available) {
		String key = "class:" + classId;
		this.redisTemplate.opsForHash().put(key, "name", name);
		this.redisTemplate.opsForHash().put(key, "available", String.valueOf(available));
	}
	
	@Override
    public boolean hasBooked(String classId, String userId) {
        String usersKey = "class:" + classId + ":users";
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(usersKey, userId));
    }
	
	@Override
	public Map<Object, Object> getClass(String classId) {
		return this.redisTemplate.opsForHash().entries("class:" + classId);
    }
	
	@Override
	public void createStringKey(String key, String value) {
		this.redisTemplate.opsForValue().set(key, value);
	}

	@Transactional
	@Override
	public boolean bookClass(String classId, String userId) {
		String availableKey = "class:" + classId + ":available";
		String usersKey = "class:" + classId + ":users";
		
		this.redisTemplate.watch(availableKey);
	    String availableStr = redisTemplate.opsForValue().get(availableKey);

	    if (availableStr == null) {
	    	this.redisTemplate.unwatch();
	        return false;
	    }
	    
	    int available = Integer.parseInt(availableStr);
        if (available > 0 && available <= 5) {
            this.redisTemplate.setEnableTransactionSupport(true);
            this.redisTemplate.multi();
            this.redisTemplate.opsForValue().decrement(availableKey);
            this.redisTemplate.opsForSet().add(usersKey, userId);
            List<Object> execResult = this.redisTemplate.exec();

            return execResult != null && !execResult.isEmpty();
        }

        this.redisTemplate.unwatch();
		return false;
	}

	@Override
	public boolean cancelClass(String classId, String userId) {
		String availableKey = "class:" + classId + ":available";
		String usersKey = "class:" + classId + ":users";
		
		this.redisTemplate.watch(availableKey);
	    String availableStr = redisTemplate.opsForValue().get(availableKey);

	    if (availableStr == null) {
	    	this.redisTemplate.unwatch();
	        return false;
	    }
	    
	    int available = Integer.parseInt(availableStr);
        if (available > 0 && available <= 5) {
            this.redisTemplate.setEnableTransactionSupport(true);
            this.redisTemplate.multi();
            this.redisTemplate.opsForValue().increment(availableKey);
            this.redisTemplate.opsForSet().remove(usersKey, userId);
            List<Object> execResult = this.redisTemplate.exec();

            return execResult != null && !execResult.isEmpty();
        }

        this.redisTemplate.unwatch();
		return false;
	}

}
