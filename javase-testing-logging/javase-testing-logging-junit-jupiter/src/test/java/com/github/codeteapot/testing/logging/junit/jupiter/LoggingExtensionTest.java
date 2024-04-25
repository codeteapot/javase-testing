package com.github.codeteapot.testing.logging.junit.jupiter;

import static org.junit.platform.engine.TestExecutionResult.Status.FAILED;
import static org.junit.platform.engine.TestExecutionResult.Status.SUCCESSFUL;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.engine.JupiterTestEngine;
import org.junit.platform.engine.TestEngine;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.testkit.engine.EngineTestKit;

class LoggingExtensionTest {

  private TestEngine testEngine;

  @BeforeEach
  void setUp() {
    testEngine = new JupiterTestEngine();
  }

  @Test
  void injectField() {
    EngineTestKit.engine(testEngine)
        .selectors(selectClass(InjectMockLoggerFieldTest.class))
        .execute()
        .testEvents()
        .assertThatEvents()
        .hasSize(2)
        .anyMatch(event -> event.getPayload(TestExecutionResult.class)
            .map(TestExecutionResult::getStatus)
            .map(SUCCESSFUL::equals)
            .orElse(false));
  }

  @Test
  void injectFieldMissingAnnotation() {
    EngineTestKit.engine(testEngine)
        .selectors(selectClass(InjectMockLoggerFieldMissingAnnotationTest.class))
        .execute()
        .testEvents()
        .assertThatEvents()
        .hasSize(2)
        .anyMatch(event -> event.getPayload(TestExecutionResult.class)
            .map(TestExecutionResult::getStatus)
            .map(FAILED::equals)
            .orElse(false));
  }

  @Test
  void injectParameter() {
    EngineTestKit.engine(testEngine)
        .selectors(selectClass(InjectMockLoggerParameterTest.class))
        .execute()
        .testEvents()
        .assertThatEvents()
        .hasSize(2)
        .anyMatch(event -> event.getPayload(TestExecutionResult.class)
            .map(TestExecutionResult::getStatus)
            .map(SUCCESSFUL::equals)
            .orElse(false));
  }

  @Test
  void injectParameterNoHandlerType() {
    EngineTestKit.engine(testEngine)
        .selectors(selectClass(InjectMockLoggerParameterNoHandlerTypeTest.class))
        .execute()
        .testEvents()
        .assertThatEvents()
        .hasSize(2)
        .anyMatch(event -> event.getPayload(TestExecutionResult.class)
            .map(TestExecutionResult::getStatus)
            .map(FAILED::equals)
            .orElse(false));
  }

  @Test
  void injectParameterMissingAnnotation() {
    EngineTestKit.engine(testEngine)
        .selectors(selectClass(InjectMockLoggerParameterMissingAnnotationTest.class))
        .execute()
        .testEvents()
        .assertThatEvents()
        .hasSize(2)
        .anyMatch(event -> event.getPayload(TestExecutionResult.class)
            .map(TestExecutionResult::getStatus)
            .map(FAILED::equals)
            .orElse(false));
  }
}
