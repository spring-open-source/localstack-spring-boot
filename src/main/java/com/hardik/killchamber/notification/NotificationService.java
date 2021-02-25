package com.hardik.killchamber.notification;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.hardik.killchamber.notification.configuration.NotificationConfiguration;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableConfigurationProperties(NotificationConfiguration.class)
public class NotificationService {

	private final NotificationConfiguration notificationConfiguration;

	private final AmazonSNSClient amazonSNSClient;

	public void publishMessage(String message) {
		var configuration = notificationConfiguration.getProperties();
		amazonSNSClient.publish(configuration.getTopicArn(), message, "Subject");
	}

}