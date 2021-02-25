package com.hardik.killchamber.utility;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hardik.killchamber.pojo.TargetDto;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class ProcessTargetUtility {

	public String convert(List<TargetDto> targetDtos) {
		try {
			return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(targetDtos);
		} catch (JsonProcessingException e) {
			log.error("Unable to Convert List of PotentialTargetDtos to String");
			throw new RuntimeException();
		}
	}

}
