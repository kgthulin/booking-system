package com.system.booking.service;

import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

import com.system.booking.job.EndClassJob;

@Service
public class ClassSchedulerService {

	private final Scheduler scheduler;

	public ClassSchedulerService(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	public void scheduleEndClassJob(String classId, Date endTime) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(EndClassJob.class)
                .withIdentity("endClassJob-" + classId, "class-jobs")
                .usingJobData("classId", classId)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("endClassTrigger-" + classId, "class-triggers")
                .startAt(endTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withMisfireHandlingInstructionFireNow())
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }
	
	public void cancelEndClassJob(String classId) throws SchedulerException {
        JobKey jobKey = new JobKey("endClassJob-" + classId, "class-jobs");
        scheduler.deleteJob(jobKey);
    }
}
