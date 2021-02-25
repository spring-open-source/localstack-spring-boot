package com.hardik.killchamber.service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hardik.killchamber.entity.Person;
import com.hardik.killchamber.pojo.TargetDto;
import com.hardik.killchamber.repository.PersonRepository;
import com.hardik.killchamber.storage.StorageService;
import com.hardik.killchamber.utility.ImageKeyUtility;
import com.hardik.killchamber.utility.StringToObjectConversionUtility;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonService {

	private final PersonRepository personRepository;

	private final StorageService storageService;

	public UUID create(String personCreationRequest, MultipartFile personImage) {
		final var personCreationRequestObject = StringToObjectConversionUtility.convert(personCreationRequest);
		final var person = new Person();
		person.setEmail(personCreationRequestObject.getEmail());
		person.setFullName(personCreationRequestObject.getFullName());
		person.setAge(personCreationRequestObject.getAge());

		final var imageKey = ImageKeyUtility.generate(person);
		person.setImageKey(imageKey);

		storageService.save(imageKey, personImage);

		final var savedPerson = personRepository.save(person);
		return savedPerson.getId();
	}

	public Person retreivePerson(UUID personId) {
		return personRepository.findById(personId).orElseThrow();
	}

	public List<TargetDto> getPotentialTargets() {
		return personRepository.findAll().stream().filter(person -> person.getSpared() == null)
				.map(person -> TargetDto.builder().id(person.getId()).build()).collect(Collectors.toList());
	}

	public void decideFate(UUID personId) {
		final var person = retreivePerson(personId);
		person.setSpared(new Random().ints(1, 3).findAny().getAsInt() == 1 ? true : false);
		personRepository.save(person);
	}

}
