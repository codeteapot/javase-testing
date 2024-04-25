package com.github.codeteapot.testing.logging.junit.jupiter;

import static java.util.logging.Logger.getLogger;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

import java.util.logging.Handler;
import java.util.logging.Logger;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.github.codeteapot.testing.logging.mockito.MockLogger;

@ExtendWith(LoggingExtension.class)
@Tag("sample")
class InjectMockLoggerParameterTest {

  private static final String LOGGER_NAME = "test.Logger";

  private static final String SOME_MESSAGE = "Some message";

  private static final Logger logger = getLogger(LOGGER_NAME);

  @Test
  void testIt(@MockLogger(name = LOGGER_NAME) Handler handler) {
    logger.finest(SOME_MESSAGE);

    verify(handler).publish(argThat(record -> record.getMessage().equals(SOME_MESSAGE)));
  }
}
