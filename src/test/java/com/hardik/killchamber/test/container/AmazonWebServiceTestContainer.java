package com.hardik.killchamber.test.container;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SNS;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.hardik.killchamber.notification.configuration.NotificationConfiguration;
import com.hardik.killchamber.queue.configuration.QueueConfiguration;
import com.hardik.killchamber.storage.configuration.AmazonS3Configuration;

import net.bytebuddy.utility.RandomString;

@Testcontainers
public class AmazonWebServiceTestContainer {

	public final static LocalStackContainer container;

	public final static String bucketName;

	public final static String topicArn;

	public final static String queueName;
	
	private final static AmazonSNSClient amazonSnsClient;
	
	private final static AmazonSQSAsync amazonSQSAsync; 
	
	private final static AmazonS3 amazonS3;

	static {
		container = new LocalStackContainer(DockerImageName.parse("localstack/localstack:0.11.3")).withServices(S3, SNS,
				SQS);

		if (!(container.isCreated() && container.isRunning()))
			container.start();

		// MAKE S3 Bucket
		amazonS3 =  AmazonS3ClientBuilder.standard().withEndpointConfiguration(container.getEndpointConfiguration(S3))
				.withCredentials(container.getDefaultCredentialsProvider()).build();
		bucketName = amazonS3
				.createBucket(RandomString.make(8).toLowerCase()).getName();

		// INIT SNS CLIENT
		amazonSnsClient = (AmazonSNSClient)AmazonSNSClientBuilder.standard()
				.withEndpointConfiguration(container.getEndpointConfiguration(SNS))
				.withCredentials(container.getDefaultCredentialsProvider()).build();

		// CREATE SNS TOPIC
		topicArn = amazonSnsClient.createTopic(RandomString.make(7)).getTopicArn();

		// STATIC QUEUE NAME TO MATCH @SqsListener CODE
		queueName = "killchamber-queue";

		// INIT SQS CLIENT
		amazonSQSAsync = (AmazonSQSAsync)AmazonSQSClientBuilder.standard()
				.withEndpointConfiguration(container.getEndpointConfiguration(SQS))
				.withCredentials(container.getDefaultCredentialsProvider()).build();
		
		amazonSQSAsync.createQueue(queueName);

		// SUBSCRIBE SQS TO SNS TOPIC
		//SubscribeRequest request = new SubscribeRequest(topicArn, "sqs", null);
		//amazonSnsClient.subscribe(request);

	}

	@Primary
	@Bean
	@Profile("testing")
	public AmazonSNSClient getSnsClient() {
		return amazonSnsClient;
	}

	@Primary
	@Bean
	@Profile("testing")
	public AmazonSQSAsync amazonSQSAsync() {
		return amazonSQSAsync;
	}

	@Bean
	@Profile("testing")
	public AmazonS3 amazonS3() {
		return amazonS3;
	}

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("cloud.aws.region.static", container::getRegion);

		registry.add("com.hardik.killchamber.storage.access-key", container::getAccessKey);
		registry.add("com.hardik.killchamber.storage.secret-key", container::getSecretKey);
		registry.add("com.hardik.killchamber.storage.region", container::getRegion);
		registry.add("com.hardik.killchamber.storage.bucket-name", () -> bucketName);

		registry.add("com.hardik.killchamber.sns.properties.access-key", container::getAccessKey);
		registry.add("com.hardik.killchamber.sns.properties.secret-key", container::getSecretKey);
		registry.add("com.hardik.killchamber.sns.properties.topic-arn", () -> topicArn);

		registry.add("com.hardik.killchamber.sqs.properties.access-key", container::getAccessKey);
		registry.add("com.hardik.killchamber.sqs.properties.secret-key", container::getSecretKey);
		registry.add("com.hardik.killchamber.sqs.properties.queue-name", () -> queueName);
	}

}
