package com.hardik.killchamber.test;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hardik.killchamber.notification.configuration.NotificationConfiguration;
import com.hardik.killchamber.queue.configuration.QueueConfiguration;
import com.hardik.killchamber.service.PersonService;
import com.hardik.killchamber.storage.configuration.AmazonS3Configuration;
import com.hardik.killchamber.test.container.AmazonWebServiceTestContainer;
import com.hardik.killchamber.test.container.PostgresqlTestContainer;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LocalstackSpringBootPocApplicationTests extends PostgresqlTestContainer{
	
	private final  PersonService personService;
	
	@Autowired
	public LocalstackSpringBootPocApplicationTests(PersonService personService) {
		super();
		this.personService = personService;
	}

	@Nested
	public class Demo extends AmazonWebServiceTestContainer{

		@Test
		void contextLoads() {
			MockMultipartFile file = new MockMultipartFile("abc.png", "HARDIK SINGH BEHL".getBytes());
			personService.create("{\"email\":\"behlHardik@gmail.com\",\"fullName\":\"behlHardik\",\"age\":28}", file);
		}
	}

}
