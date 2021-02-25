package com.hardik.killchamber.notification.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.hardik.killchamber.sns")
public class NotificationConfiguration {
	
	private Configuration properties = new Configuration();

	@Data
	public class Configuration {
		private String accessKey;
		private String secretKey;
		private String topicArn;
	}

}