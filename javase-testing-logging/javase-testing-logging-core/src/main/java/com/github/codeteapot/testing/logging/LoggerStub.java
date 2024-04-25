package com.github.codeteapot.testing.logging;

import static java.util.Objects.requireNonNull;
import static java.util.logging.Level.ALL;
import static java.util.logging.Logger.getLogger;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stub of a Java Standard Edition logger.
 */
public class LoggerStub {

  private final Level previousLevel;
  private final PreviousHandlers previousHandlers;
  private final Logger logger;
  private final Handler handler;

  private LoggerStub(String name, Handler handler) {
    logger = getLogger(name);
    previousLevel = logger.getLevel();
    logger.setLevel(ALL);
    previousHandlers = new PreviousHandlers(logger);
    logger.addHandler(handler);
    this.handler = requireNonNull(handler);
  }

  /**
   * Restore the handlers prior to the creation of the stub, as well as the logging level it
   * handled.
   */
  public void restore() {
    logger.setLevel(previousLevel);
    logger.removeHandler(handler);
    previousHandlers.restore();
  }

  /**
   * Creates a stub for the logger with the given name and replaces its handlers with the specified
   * handler.
   *
   * <p>The {@link Level#ALL} level is set for the logger involved.
   *
   * @param name Name of the logger for which the stub is created.
   * @param handler Handler that will replace the previous handlers of the logger involved.
   *
   * @return The logger stub.
   */
  public static LoggerStub stubFor(String name, Handler handler) {
    return new LoggerStub(name, handler);
  }
}
