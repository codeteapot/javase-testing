package com.github.codeteapot.testing.logging.hamcrest;

import static com.github.codeteapot.testing.logging.hamcrest.SomeLogRecordMatcher.someLogRecord;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.jupiter.api.Test;

class SomeLogRecordMatcherTest {

  private static final Level SOME_LEVEL = INFO;
  private static final String SOME_MESSAGE = "Some message";
  private static final Exception SOME_EXCEPTION = new Exception();

  private static final String SOME_LEVEL_STR = "INFO";
  private static final String SOME_EXCEPTION_STR = "java.lang.Exception";

  private static final Level ANOTHER_LEVEL = SEVERE;
  private static final String ANOTHER_MESSAGE = "Another message";
  private static final Exception ANOTHER_EXCEPTION = new Exception();

  @Test
  void matchAll() {
    SomeLogRecordMatcher matcher = someLogRecord()
        .withLevel(equalTo(SOME_LEVEL))
        .withMessage(equalTo(SOME_MESSAGE))
        .withThrown(equalTo(SOME_EXCEPTION));

    LogRecord record = new LogRecord(SOME_LEVEL, SOME_MESSAGE);
    record.setThrown(SOME_EXCEPTION);
    boolean result = matcher.matches(record);

    assertThat(result).isTrue();
  }

  @Test
  void matchWithoutLevel() {
    SomeLogRecordMatcher matcher = someLogRecord()
        .withMessage(equalTo(SOME_MESSAGE))
        .withThrown(equalTo(SOME_EXCEPTION));

    LogRecord record = new LogRecord(ANOTHER_LEVEL, SOME_MESSAGE);
    record.setThrown(SOME_EXCEPTION);
    boolean result = matcher.matches(record);

    assertThat(result).isTrue();
  }

  @Test
  void matchWithoutMessage() {
    SomeLogRecordMatcher matcher = someLogRecord()
        .withLevel(equalTo(SOME_LEVEL))
        .withThrown(equalTo(SOME_EXCEPTION));

    LogRecord record = new LogRecord(SOME_LEVEL, ANOTHER_MESSAGE);
    record.setThrown(SOME_EXCEPTION);
    boolean result = matcher.matches(record);

    assertThat(result).isTrue();
  }

  @Test
  void matchWithoutThrown() {
    SomeLogRecordMatcher matcher = someLogRecord()
        .withLevel(equalTo(SOME_LEVEL))
        .withMessage(equalTo(SOME_MESSAGE));

    LogRecord record = new LogRecord(SOME_LEVEL, SOME_MESSAGE);
    record.setThrown(ANOTHER_EXCEPTION);
    boolean result = matcher.matches(record);

    assertThat(result).isTrue();
  }

  @Test
  void notMatchBecauseLevel() {
    SomeLogRecordMatcher matcher = someLogRecord()
        .withLevel(equalTo(SOME_LEVEL))
        .withMessage(equalTo(SOME_MESSAGE))
        .withThrown(equalTo(SOME_EXCEPTION));

    LogRecord record = new LogRecord(ANOTHER_LEVEL, SOME_MESSAGE);
    record.setThrown(SOME_EXCEPTION);
    boolean result = matcher.matches(record);

    assertThat(result).isFalse();
  }

  @Test
  void notMatchBecauseMessage() {
    SomeLogRecordMatcher matcher = someLogRecord()
        .withLevel(equalTo(SOME_LEVEL))
        .withMessage(equalTo(SOME_MESSAGE))
        .withThrown(equalTo(SOME_EXCEPTION));

    LogRecord record = new LogRecord(SOME_LEVEL, ANOTHER_MESSAGE);
    record.setThrown(SOME_EXCEPTION);
    boolean result = matcher.matches(record);

    assertThat(result).isFalse();
  }

  @Test
  void notMatchBecauseThrown() {
    SomeLogRecordMatcher matcher = someLogRecord()
        .withLevel(equalTo(SOME_LEVEL))
        .withMessage(equalTo(SOME_MESSAGE))
        .withThrown(equalTo(SOME_EXCEPTION));

    LogRecord record = new LogRecord(SOME_LEVEL, SOME_MESSAGE);
    record.setThrown(ANOTHER_EXCEPTION);
    boolean result = matcher.matches(record);

    assertThat(result).isFalse();
  }

  @Test
  void fullDescription() {
    SomeLogRecordMatcher matcher = someLogRecord()
        .withLevel(equalTo(SOME_LEVEL))
        .withMessage(equalTo(SOME_MESSAGE))
        .withThrown(equalTo(SOME_EXCEPTION));

    Description description = new StringDescription();
    matcher.describeTo(description);

    assertThat(description.toString())
        .contains(SOME_LEVEL_STR)
        .contains(SOME_MESSAGE)
        .contains(SOME_EXCEPTION_STR);
  }
}
