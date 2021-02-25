package com.hardik.killchamber.queue.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.hardik.killchamber.sqs")
public class QueueConfiguration {
	
	private Configuration properties = new Configuration();

	@Data
	public class Configuration {
		private String accessKey;
		private String secretKey;
		private String queueName;
	}

}