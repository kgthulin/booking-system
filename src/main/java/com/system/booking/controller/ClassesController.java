package com.system.booking.controller;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.system.booking.entity.Classes;
import com.system.booking.entity.Package;
import com.system.booking.exception.InsufficientCreditException;
import com.system.booking.exception.ResourceNotFoundException;
import com.system.booking.service.BookingService;
import com.system.booking.service.ClassesService;
import com.system.booking.service.PackageService;

@RequestMapping("api/classes/")
@RestController
public class ClassesController {
	
	private static final int MAX_AVAILABLE_SLOTS = 5;
	public static ConcurrentLinkedQueue<Map<String, String>> queue = new ConcurrentLinkedQueue<>();
	
	private final ClassesService classesService;
	private final BookingService bookingService;
	private final PackageService packageService;
	
    public ClassesController(ClassesService classesService, BookingService bookingService, PackageService packageService) {
		this.classesService = classesService;
		this.bookingService = bookingService;
		this.packageService = packageService;
	}

	@PostMapping("/new")
    public ResponseEntity<Classes> createClass(@RequestBody Classes newClasses, @RequestParam("packageId") Long packId) {
		Package packagge = this.packageService.findPackageById(packId).orElseThrow(() -> new ResourceNotFoundException());
		Classes classes = new Classes(newClasses.getName(), 5, newClasses.getCredit(), newClasses.getStartTime(), newClasses.getEndTime(), packagge);
		Classes createdClasses = this.classesService.createClasses(classes);
		this.bookingService.createClass(createdClasses.getId(), createdClasses.getName(), MAX_AVAILABLE_SLOTS);
		this.bookingService.createStringKey("class:" + createdClasses.getId() + ":available", String.valueOf(MAX_AVAILABLE_SLOTS));
    	return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(createdClasses);
    }
	
	@GetMapping("/{classId}")
    public ResponseEntity<Map<Object, Object>> getClass(@PathVariable String classId) {
		Map<Object, Object> result = this.bookingService.getClass(classId);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(result);
    }
	
	@PostMapping("/{classId}/book/{userId}")
    public ResponseEntity<String> bookClass(@PathVariable String classId, @PathVariable String userId) {
    	Classes classes = this.classesService.findClassesById(Long.parseLong(classId)).orElseThrow(() -> new ResourceNotFoundException());
    	Package packagge = classes.getPackagge();
    	int updatedCredit = packagge.getCredit() - classes.getCredit();
    	if (updatedCredit < 0) {
    		throw new InsufficientCreditException();
    	}
    	packagge.setCredit(updatedCredit);
		
        if (this.bookingService.hasBooked(classId, userId)) {
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("User already booked!");
        }

        boolean success = this.bookingService.bookClass(classId, userId);
        
        if (!success) {
        	ClassesController.queue.offer(Map.of(classId, userId));
        }
        
        String result = success ? "Booking successful!" : "Class is full!";
        
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(result);
    }
	
	@PostMapping("/{classId}/cancel/{userId}")
    public ResponseEntity<String> cancelClass(@PathVariable String classId, @PathVariable String userId) {
    	Classes classes = this.classesService.findClassesById(Long.parseLong(classId)).orElseThrow(() -> new ResourceNotFoundException());
    	Date currentDate = new Date();
    	long startTime = classes.getStartTime().getTime();
    	long currentTime = currentDate.getTime();
    	long diffTime = startTime - currentTime;
    	long diffInHours = diffTime / (60 * 60 * 1000);
    	
    	if (diffInHours > 4) {
        	Package packagge = classes.getPackagge();
        	int updatedCredit = packagge.getCredit() + classes.getCredit();
        	packagge.setCredit(updatedCredit);
    	}
		
        boolean success = this.bookingService.cancelClass(classId, userId);
        
        if (success && !queue.isEmpty()) {
        	Map<String, String> map = ClassesController.queue.poll();
        	String key = map.keySet().stream().findFirst().get();
        	String value = map.get(key);
        	this.bookingService.bookClass(key, value);
        }
        
        String result = success ? "Booking cancel successful!" : "Booking cancel fail!";
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(result);
    }
}
