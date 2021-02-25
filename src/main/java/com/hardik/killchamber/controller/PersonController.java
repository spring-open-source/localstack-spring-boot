package com.hardik.killchamber.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hardik.killchamber.entity.Person;
import com.hardik.killchamber.service.PersonService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PersonController {

	private final PersonService personService;

	@PostMapping("v1/person")
	public UUID personCreationHandler(@RequestPart(name = "data", required = true) final String personCreationRequest,
			@RequestPart(name = "file", required = true) final MultipartFile personImage)
			throws JsonMappingException, JsonProcessingException {
		return personService.create(personCreationRequest, personImage);
	}

	@GetMapping("v1/person/{personId}")
	public Person personRetrievalHandler(@PathVariable(name = "personId", required = true) final UUID personId) {
		return personService.retreivePerson(personId);
	}

}