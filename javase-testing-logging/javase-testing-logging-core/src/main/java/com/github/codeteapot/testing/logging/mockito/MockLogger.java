package com.github.codeteapot.testing.logging.mockito;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.github.codeteapot.testing.logging.LoggerStub;
import java.lang.annotation.Retention;
import java.util.logging.Handler;
import org.mockito.Mockito;

/**
 * Marks a handler as a substitute for a logger.
 *
 * <p>The annotated target must be of type {@link Handler}. This is assigned the result of a call to
 * {@link Mockito#mock(Class)} and used as a parameter to the underlying call to
 * {@link LoggerStub#stubFor(String, Handler)}.
 */
@Retention(RUNTIME)
public @interface MockLogger {

  /**
   * Name of the logger to replace.
   *
   * @return The name of the logger.
   */
  String name();
}
