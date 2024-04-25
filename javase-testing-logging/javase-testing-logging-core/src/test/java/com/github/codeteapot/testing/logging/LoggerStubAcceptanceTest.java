package com.github.codeteapot.testing.logging;

import static com.github.codeteapot.testing.logging.LoggerStub.stubFor;
import static java.util.logging.Level.INFO;
import static java.util.logging.Logger.getLogger;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoggerStubAcceptanceTest {

  private static final String LOGGER_NAME = LoggerStubAcceptanceTest.class.getName();

  private static final Level SOME_LEVEL = INFO;
  private static final String SOME_MESSAGE = "Some message";
  private static final Exception SOME_EXCEPTION = new Exception();

  private static final Logger logger = getLogger(LOGGER_NAME);

  @Test
  void publishOnRightHandler(@Mock Handler previousHandler, @Mock Handler newHandler) {
    LoggerStub previousStub = stubFor(LOGGER_NAME, previousHandler);
    LoggerStub newStub = stubFor(LOGGER_NAME, newHandler);

    logger.log(SOME_LEVEL, SOME_MESSAGE, SOME_EXCEPTION);

    verify(previousHandler, never()).publish(any());
    verify(newHandler).publish(argThat(record -> record.getLevel().equals(SOME_LEVEL)
        && record.getMessage().equals(SOME_MESSAGE)
        && record.getThrown().equals(SOME_EXCEPTION)));

    newStub.restore();
    reset(previousHandler, newHandler);

    logger.log(SOME_LEVEL, SOME_MESSAGE, SOME_EXCEPTION);

    verify(previousHandler).publish(argThat(record -> record.getLevel().equals(SOME_LEVEL)
        && record.getMessage().equals(SOME_MESSAGE)
        && record.getThrown().equals(SOME_EXCEPTION)));
    verify(newHandler, never()).publish(any());

    previousStub.restore();
  }
}
