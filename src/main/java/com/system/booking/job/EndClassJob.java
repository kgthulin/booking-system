package com.system.booking.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.system.booking.controller.ClassesController;
import com.system.booking.entity.Classes;
import com.system.booking.entity.Package;
import com.system.booking.exception.ResourceNotFoundException;
import com.system.booking.service.ClassesService;

@Component
public class EndClassJob implements Job {
	
	private final ClassesService classesService;
	
	public EndClassJob(ClassesService classesService) {
		this.classesService = classesService;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
//		 String classId = context.getJobDetail().getJobDataMap().getString("classId");
		 ClassesController.queue.stream().forEach(m -> {
			 String key = m.keySet().stream().findFirst().get();
			 Classes classes = this.classesService.findClassesById(Long.parseLong(key)).orElseThrow(() -> new ResourceNotFoundException());
			 Package packagge = classes.getPackagge();
			 int updatedCredit = packagge.getCredit() + classes.getCredit();
			 packagge.setCredit(updatedCredit);
		 });
	}

}
