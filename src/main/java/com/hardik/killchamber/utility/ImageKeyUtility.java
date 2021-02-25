package com.hardik.killchamber.utility;

import com.hardik.killchamber.entity.Person;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageKeyUtility {
	
	public String generate(Person person) {
		return "person-images/"+person.getEmail();
	}

}
