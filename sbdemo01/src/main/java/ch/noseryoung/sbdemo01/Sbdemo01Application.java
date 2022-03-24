package ch.noseryoung.sbdemo01;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class Sbdemo01Application {

	public static void main(String[] args) {
		log.error("helloworld Test");
		SpringApplication.run(Sbdemo01Application.class, args);
//		Logger logger = LoggerFactory.getLogger(Sbdemo01Application.class);
//		logger.error("test");
	}
}
