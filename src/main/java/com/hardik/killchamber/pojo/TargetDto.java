package com.hardik.killchamber.pojo;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class TargetDto {
	
	private final UUID id;

}
