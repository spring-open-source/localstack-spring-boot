package com.hardik.killchamber.request;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class PersonCreationRequest {

	private final String email;
	private final String fullName;
	private final Integer age;
}
