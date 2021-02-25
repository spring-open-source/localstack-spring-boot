package com.hardik.killchamber.queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.hardik.killchamber.pojo.TargetDto;
import com.hardik.killchamber.queue.configuration.QueueConfiguration;
import com.hardik.killchamber.service.PersonService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableConfigurationProperties(QueueConfiguration.class)
public class QueueListenerService {

	private final PersonService personService;
	
	private final QueueConfiguration queueConfiguration; 

	@SqsListener("killchamber-queue")
	public void loadMessageFromSQS(String message) throws JsonMappingException, JsonProcessingException {
		LinkedHashMap<String, Object> messageData = new ObjectMapper()
				.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
				.registerModule(new JavaTimeModule()).readValue(message, LinkedHashMap.class);
		final var potentialTargets = new ObjectMapper().readValue(messageData.get("Message").toString(),
				ArrayList.class);
		potentialTargets.forEach(potentialTarget -> {
			personService.decideFate(new ObjectMapper().convertValue(potentialTarget, TargetDto.class).getId());
		});
	}
}