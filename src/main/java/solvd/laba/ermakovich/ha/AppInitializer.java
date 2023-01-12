package solvd.laba.ermakovich.ha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableTransactionManagement
public class AppInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AppInitializer.class, args);
	}

}
