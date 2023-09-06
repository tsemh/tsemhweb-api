package io.tsemh.tsemhapirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "io.tsemh.tsemhapirest.entity")
public class TsemhApiRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TsemhApiRestApplication.class, args);
	}

}