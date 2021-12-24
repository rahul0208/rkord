package com.notes;

import com.azure.spring.autoconfigure.cosmos.CosmosAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@EnableAutoConfiguration(exclude = CosmosAutoConfiguration.class)
class RkordApplicationTests {
	@Test
	void contextLoads() {
	}

}