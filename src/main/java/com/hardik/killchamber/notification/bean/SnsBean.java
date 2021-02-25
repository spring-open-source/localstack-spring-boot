package com.hardik.killchamber.notification.bean;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.hardik.killchamber.notification.configuration.NotificationConfiguration;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@EnableConfigurationProperties(NotificationConfiguration.class)
public class SnsBean {

	private final NotificationConfiguration notificationConfiguration;

	@Primary
	@Bean
	@Profile("default")
	public AmazonSNSClient getSnsClient() {
		var configuration = notificationConfiguration.getProperties();
		return (AmazonSNSClient) AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_2)
				.withCredentials(new AWSStaticCredentialsProvider(
						new BasicAWSCredentials(configuration.getAccessKey(), configuration.getSecretKey())))
				.build();
	}

}