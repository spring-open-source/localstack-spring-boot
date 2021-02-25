package com.hardik.killchamber.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hardik.killchamber.notification.NotificationService;
import com.hardik.killchamber.pojo.TargetDto;
import com.hardik.killchamber.service.PersonService;
import com.hardik.killchamber.utility.ProcessTargetUtility;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class SendDailyPotentialTargetsScheduler {

	private final PersonService personService;

	private final NotificationService notificationService;

	@Scheduled(cron = "0 */30 * ? * *")
	public void sendDailyPotentialTargetsIdsScheduler() {
		List<TargetDto> potentialTargetDtos = personService.getPotentialTargets();
		notificationService.publishMessage(ProcessTargetUtility.convert(potentialTargetDtos));
	}
}
