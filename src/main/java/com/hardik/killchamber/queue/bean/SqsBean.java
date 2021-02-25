package com.hardik.killchamber.queue.bean;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.hardik.killchamber.queue.configuration.QueueConfiguration;

import lombok.AllArgsConstructor;

@Component
@EnableConfigurationProperties(QueueConfiguration.class)
@AllArgsConstructor
public class SqsBean {

	private final QueueConfiguration queueConfiguration;

	@Primary
	@Bean
	@Profile("default")
	public AmazonSQSAsync amazonSQSAsync() {
		final var queueProperties = queueConfiguration.getProperties();
		return AmazonSQSAsyncClientBuilder.standard().withRegion(Regions.US_EAST_2)
				.withCredentials(new AWSStaticCredentialsProvider(
						new BasicAWSCredentials(queueProperties.getAccessKey(), queueProperties.getSecretKey())))
				.build();
	}
}