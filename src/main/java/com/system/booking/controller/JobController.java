package com.system.booking.controller;

import java.util.Date;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.system.booking.entity.Classes;
import com.system.booking.exception.ResourceNotFoundException;
import com.system.booking.service.ClassSchedulerService;
import com.system.booking.service.ClassesService;

@RequestMapping("api/jobs/")
@RestController
public class JobController {
	
	private final ClassSchedulerService schedulerService;
	private final ClassesService classesService;
	
	public JobController(ClassSchedulerService schedulerService, ClassesService classesService) {
		this.schedulerService = schedulerService;
		this.classesService = classesService;
	}

	@PostMapping("/start")
    public ResponseEntity<String> startClass(@RequestParam String classId) throws Exception {
    	Classes classes = this.classesService.findClassesById(Long.parseLong(classId)).orElseThrow(() -> new ResourceNotFoundException());
    	Date endTime = classes.getEndTime();
        schedulerService.scheduleEndClassJob(classId, endTime);
        String result = "Class " + classId + " started. Will end at " + endTime;
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(result);
    }

}
