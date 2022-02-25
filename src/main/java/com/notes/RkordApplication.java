package com.notes;

import com.akkaserverless.javasdk.AkkaServerless;
import com.example.domain.Counter;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootApplication
public class RkordApplication {
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static AkkaServerless createAkkaServerless() {
		// The AkkaServerlessFactory automatically registers any generated Actions, Views or Entities,
		// and is kept up-to-date with any changes in your protobuf definitions.
		// If you prefer, you may remove this and manually register these components in a
		// `new AkkaServerless()` instance.
		return AkkaServerlessFactory.withComponents(
				Counter::new);
	}

	public static void main(String[] args) {
		LOG.info("starting the Akka Serverless service");
		createAkkaServerless().start();
		SpringApplication.run(RkordApplication.class, args);
	}

}
