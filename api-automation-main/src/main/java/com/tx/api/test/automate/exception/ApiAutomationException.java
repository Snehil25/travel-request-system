package com.tx.api.test.automate.exception;

public class ApiAutomationException extends RuntimeException {

  /**
   * Pass the message that needs to be appended to the stacktrace
   *
   * @param message Details about the exception or custom message
   */
  public ApiAutomationException(String message) {
    super(message);
  }

  /**
   * @param message Details about the exception or custom message
   * @param cause   Pass the enriched stacktrace or customised stacktrace
   */
  public ApiAutomationException(String message, Throwable cause) {
    super(message, cause);
  }

}