package com.imgarena.dde;

import com.imgarena.dde.client.DDEConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DdeSingularApplication implements ApplicationRunner {
  private static final Logger LOG = LoggerFactory.getLogger(DdeSingularApplication.class);

  private final DDEConnector ddeConnector;

  public DdeSingularApplication(DDEConnector ddeConnector) {
    this.ddeConnector = ddeConnector;
  }

  public static void main(String[] args) {
    SpringApplication.run(DdeSingularApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    var eventId = args.getNonOptionArgs().get(0);
    LOG.info("Listening score for event: {}", eventId);
    ddeConnector.listen(eventId, "6079750");
  }
}
