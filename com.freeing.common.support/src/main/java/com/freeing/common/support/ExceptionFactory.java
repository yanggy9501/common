package com.freeing.common.support;

public class ExceptionFactory {

  private ExceptionFactory() {

  }

  public static RuntimeException wrapException(String message, Exception e) {
    return new RuntimeException(ExceptionContext.instance().message(message).cause(e).toString(), e);
  }

}
