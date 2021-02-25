package com.hardik.killchamber.utility;

import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.hardik.killchamber.request.PersonCreationRequest;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class StringToObjectConversionUtility {

	public PersonCreationRequest convert(String personCreationRequest) {
		try {
			return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
					.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
					.registerModule(new JavaTimeModule()).readValue(personCreationRequest, PersonCreationRequest.class);
		} catch (JsonProcessingException e) {
			log.error("UNABLE TO CONVERT PERSON CREATION REQUEST STRING TO OBJECT " + LocalDateTime.now());
			throw new RuntimeException();
		}
	}

}
