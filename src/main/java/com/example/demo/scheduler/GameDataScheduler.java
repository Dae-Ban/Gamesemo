package com.example.demo.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.task.GameDataTask;

@Component
public class GameDataScheduler {
	@Autowired
	private GameDataTask task;
	
	@Scheduled(cron = "0 0 4 * * *")
	public void run() {
		System.out.println("스케쥴러 실행");
		task.run();
	}
}
