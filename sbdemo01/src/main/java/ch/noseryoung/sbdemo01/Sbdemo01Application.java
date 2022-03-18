package ch.noseryoung.sbdemo01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Sbdemo01Application {

	public static void main(String[] args) {
		SpringApplication.run(Sbdemo01Application.class, args);
//		Logger logger = LoggerFactory.getLogger(Sbdemo01Application.class);
//		logger.error("test");
	}
}
