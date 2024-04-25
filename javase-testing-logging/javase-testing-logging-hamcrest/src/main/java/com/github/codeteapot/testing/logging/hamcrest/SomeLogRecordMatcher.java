package com.github.codeteapot.testing.logging.hamcrest;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.stream.Stream;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Hamcrest matcher for a log record.
 */
public class SomeLogRecordMatcher extends TypeSafeMatcher<LogRecord> {

  private Matcher<Level> level;
  private Matcher<String> message;
  private Matcher<Throwable> thrown;

  private SomeLogRecordMatcher() {
    level = null;
    message = null;
    thrown = null;
  }

  /**
   * Add a requirement for the {@code level} field.
   *
   * @param level The matcher for the {@code level} field requirement.
   *
   * @return The matcher itself.
   */
  public SomeLogRecordMatcher withLevel(Matcher<Level> level) {
    this.level = requireNonNull(level);
    return this;
  }

  /**
   * Add a requirement for the {@code message} field.
   *
   * @param message The matcher for the {@code message} field requirement.
   *
   * @return The matcher itself.
   */
  public SomeLogRecordMatcher withMessage(Matcher<String> message) {
    this.message = requireNonNull(message);
    return this;
  }

  /**
   * Add a requirement for the {@code thrown} field.
   *
   * @param thrown The matcher for the {@code thrown} field requirement.
   *
   * @return The matcher itself.
   */
  public SomeLogRecordMatcher withThrown(Matcher<Throwable> thrown) {
    this.thrown = requireNonNull(thrown);
    return this;
  }

  /**
   * Describe the matcher with all its requirements.
   */
  @Override
  public void describeTo(Description description) {
    description.appendList("[", ",", "]", Stream.of(level, message, thrown)
        .filter(Objects::nonNull)
        .collect(toList()));
  }

  /**
   * Create a {@link LogRecord} matcher without any requirements.
   *
   * @return The created matcher.
   */
  public static SomeLogRecordMatcher someLogRecord() {
    return new SomeLogRecordMatcher();
  }

  /**
   * It matches if, and only if, all requirements are satisfied.
   */
  @Override
  protected boolean matchesSafely(LogRecord item) {
    if (level != null && !level.matches(item.getLevel())) {
      return false;
    }
    if (message != null && !message.matches(item.getMessage())) {
      return false;
    }
    if (thrown != null && !thrown.matches(item.getThrown())) {
      return false;
    }
    return true;
  }
}
