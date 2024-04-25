package com.github.codeteapot.testing.logging.junit.jupiter;

import static org.mockito.Mockito.mock;

import com.github.codeteapot.testing.logging.LoggerStub;
import com.github.codeteapot.testing.logging.mockito.MockLogger;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Handler;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Maintains the logger stubs of the replaced handlers.
 */
@ExtendWith(MockitoExtension.class)
public final class LoggingExtension
    implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

  private final Set<LoggerStub> stubs;

  /**
   * Create the extension without any stub.
   */
  public LoggingExtension() {
    stubs = new HashSet<>();
  }

  /**
   * Creates a stub for each field of the test instance that is of type {@link Handler} and is
   * annotated with {@link MockLogger}.
   *
   * @throws Exception In case the stub cannot be injected.
   */
  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    Object instance = context.getRequiredTestInstance();
    for (Field field : instance.getClass().getDeclaredFields()) {
      if (field.getType().isAssignableFrom(Handler.class)) {
        MockLogger logger = field.getAnnotation(MockLogger.class);
        if (logger != null) {
          Handler handler = mock(Handler.class);
          stubs.add(LoggerStub.stubFor(logger.name(), handler));
          boolean accessible = field.isAccessible();
          field.setAccessible(true);
          field.set(instance, handler);
          field.setAccessible(accessible);
        }
      }
    }
  }

  /**
   * The stubs created are discarded while the associated loggers are restored.
   */
  @Override
  public void afterEach(ExtensionContext context) {
    stubs.forEach(LoggerStub::restore);
    stubs.clear();
  }

  /**
   * Evaluates to {@code true} when the parameter is of type {@link Handler} and is annotated with
   * {@link MockLogger}.
   */
  @Override
  public boolean supportsParameter(
      ParameterContext parameterContext,
      ExtensionContext extensionContext) {
    return parameterContext.getParameter().getType().isAssignableFrom(Handler.class)
        && parameterContext.isAnnotated(MockLogger.class);
  }

  /**
   * Create a stub for the parameter to be resolved.
   */
  @Override
  public Object resolveParameter(
      ParameterContext parameterContext,
      ExtensionContext extensionContext) {
    MockLogger logger = parameterContext.findAnnotation(MockLogger.class)
        .get(); // Checked by supportsParameter method
    Handler handler = mock(Handler.class);
    stubs.add(LoggerStub.stubFor(logger.name(), handler));
    return handler;
  }
}
