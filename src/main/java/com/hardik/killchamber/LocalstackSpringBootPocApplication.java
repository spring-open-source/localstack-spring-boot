package com.hardik.killchamber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = { ContextStackAutoConfiguration.class, ContextRegionProviderAutoConfiguration.class })
@EnableScheduling
public class LocalstackSpringBootPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalstackSpringBootPocApplication.class, args);
	}

}
